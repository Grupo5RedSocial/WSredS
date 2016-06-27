package org.ws.accesoDB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.ws.util.conexion.ConexionBase;
import org.ws.util.entidad.AuthorBean;
import org.ws.util.entidad.ListaMed;
import org.ws.util.entidad.PostBean;
import org.ws.util.entidad.Usuario;
import org.ws.util.entidad.User;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.ws.util.StringEncrypt;


public class PostBeanDB {
	
	private AuthorBean currentUser;
	
	public String key = "92AE31A79FEEB2A3"; //llave
	public String iv = "0123456789ABCDEF"; // vector de inicialización
	public StringEncrypt encrypt;
	
	
	/**
	 * Currently logged in user
	 */
	public AuthorBean getCurrentUser() {
		return this.currentUser;
	}

	private ConexionBase conexion;

	public void setConexion(ConexionBase conexion) {
		this.conexion = conexion;
	}

	public AuthorBean getPersona(Integer id) throws Exception{
		AuthorBean autor=null;
		ResultSet rs = conexion
				.ejecutarServicio_SAS("SELECT [id_persona],[nombre],[estado],[imagen],[correo] FROM [redSocial].[dbo].[RsPersonas] where [id_persona] = "+ id);
		if (rs.next()) {
			autor = new AuthorBean();
			autor.setAvatar(rs.getString("imagen"));
			autor.setId_persona(rs.getInt("id_persona"));
			autor.setName(rs.getString("nombre"));
			autor.setEmail(rs.getString("correo"));
		}
		rs.close();
		return autor;
	}
	
	//private Session sess = Sessions.getCurrent();
	private Usuario userLogin;
	
	public List<PostBean> listaPost(Integer id) throws Exception {

		List<PostBean> posts = new ArrayList<PostBean>();

		ResultSet rs = conexion
				.ejecutarServicio_SAS("select id_persona, nombre, imagen, correo ,tipo,fechaPublicacion,publicacion,id_publicacion,imagen_p, "
						+ " isnull(nombreComenta,nombre)nombreComenta, isnull(imagen_comenta,imagen)imagen_comenta,isnull(fechaComentario,fechaPublicacion)fechaComentario,id_comentario, isnull(comentario,publicacion)comentario  from "
						+ " (select a.id_persona, a.nombre, a.imagen, a.correo ,b.tipo, getdate() as fechaPublicacion, b.publicacion, b.id_publicacion, b.imagen as imagen_p"
						+ " from [redSocial].[dbo].[RsPersonas] a, [redSocial].[dbo].RsPublicaciones b"
						+ " where (a.id_persona in (select b.id_persona_contacto from redSocial.dbo.RsContactos b  " 
						+ " where b.id_persona = "+id+" ) or a.id_persona = "+id+")"
						+ " and a.id_persona = b.id_persona) p " 
						+ " LEFT JOIN (select d.nombre nombreComenta, d.imagen imagen_comenta, getdate() AS fechaComentario, e.id_publicacion as id_publicacion1, e.id_comentario, e.comentario from [redSocial].[dbo].RsPersonas d, [redSocial].[dbo].RsComentarios e"
                        + " where d.id_persona = e.id_persona) c"
                        + " ON p.id_publicacion = c.id_publicacion1"
                        + " order by p.id_publicacion desc, c.id_comentario desc, id_persona");
		
		Integer idPAnt = 0;
		Integer idPubAct;
		PostBean post = new PostBean();
		PostBean comen = new PostBean();
		Calendar now = Calendar.getInstance();
		int conta =0;
		while (rs.next()) {
			idPubAct = rs.getInt("id_publicacion");
			//userLogin = (Usuario) sess.getAttribute("userCredential");
			//currentUser = userLogin.getUsuarioLogin();
			if (idPAnt != idPubAct) {
				String tipo = rs.getString("tipo");
				posts.add(post);
				post.setLikeList(getLikePublicacion(idPubAct));
				
				post = new PostBean();
				post.setAuthor(new AuthorBean(rs.getString("nombre"), rs
						.getString("imagen"),rs.getString("correo")));
				String publicacion = null;
				if (rs.getString("publicacion").equals("null"))
					publicacion = "T-Soul";
				else
					publicacion = rs.getString("publicacion");

				if (!tipo.equals("null")){
				if ("image/jpeg".equals(tipo)) {
					post.setContent("<p>"+ publicacion+"</p><p><img src='"+ rs.getString("imagen_p")+"' height='50px' width='50px'/></p>");
				}
				if ("video/mp4".equals(tipo)) {
					post.setContent("<p>"+ publicacion+"</p><p> <video controls='true' autoplay='false' loop='loop' height='150px' width='300px'><source src='"+ rs.getString("imagen_p")+"' type='"+rs.getString("tipo")+"'/></video></p>");
				}
				if ("application/octet-stream".equals(tipo)) {
					BufferedReader bf = new BufferedReader(new FileReader("C:/Users/Administrador/Documents/Instaladores/workspace/redS/redS/WebContent/" + rs.getString("imagen_p")));
					String sCadena = "";
					String line = "";
					while ((line = bf.readLine())!=null) {
						if (sCadena != null)
							sCadena += line;
						else
							sCadena = line;
						
						System.out.println(sCadena);
						}
				post.setContent("<p>"+ publicacion +"</p><p>"
						+ " <html> "
						+ " 	<body> "
						+ " 	<center> "
						+ " 	<script type='text/javascript' src='js/wz_jsgraphics.js'></script> "
						+ "     <script type='text/javascript' src='js/ecg.js'></script> "
						+ " 	<table> "
						+ " 	<tr> "
						+ " 	<td width=100%> "
						+ " 	<div id='Canvas"+conta+"' style='background-image: url(images/ecg_back.png);position:relative;height:250px;width:1000px;'></div> "
						+ " 	</td> "
						+ " 	</tr> "
						+ " 	</table> "
						+ " 	<img src='images/prev.gif' onclick='miVisor"+conta+".stopAuto();miVisor"+conta+".drawPrev();' style='border:0;width:10px;height:10px;'/> "
						+ " 	<img src='images/next.gif' onclick='miVisor"+conta+".stopAuto();miVisor"+conta+".drawNext();' style='border:0;width:10px;height:10px;'/> "
						+ " 	</center> "
						+ " 	<script> "
						+ " 	var cadena"+conta+" = new Array("+sCadena+"); "
						+ " 	var miVisor"+conta+" = new Visor(cadena"+conta+"); "
						+ " 	miVisor"+conta+".draw('Canvas"+conta+"','pages"+conta+"'); "
						+ " 	</script> "
						+ " </body> "
						+ " </html> "					
						+ "	</p>");
				}}else
					post.setContent("<p>"+ rs.getString("publicacion")+"</p>");
				
				post.setId_pub(rs.getInt("id_publicacion"));
				Date fecha= rs.getTimestamp("fechaPublicacion");
				post.setTime(fecha);

				now.add(Calendar.WEEK_OF_YEAR, -1);
				now.add(Calendar.MINUTE, -16);
				idPAnt = idPubAct;
			}
			
			List<PostBean> commentList = post.getCommentList();
			//commentList.add(post);
			
			comen = new PostBean();
			comen.setAuthor(new AuthorBean(rs.getString("nombreComenta"), rs
					.getString("imagen_comenta"),rs.getString("correo")));
			comen.setContent("<p>" + rs.getString("comentario") + "</p>");
			Date fecha= rs.getTimestamp("fechaPublicacion");
			comen.setTime(fecha);        
			now.add(Calendar.WEEK_OF_YEAR, -1);
			now.add(Calendar.MINUTE, -16);
			commentList.add(comen);
			//post.getCommentList().add(comen);
			conta = conta +1;
		}
		posts.add(post);
		posts.remove(0);
		rs.close();
		return posts;
	}
	
	public List<AuthorBean> getLikePublicacion(Integer id) throws Exception {
		List<AuthorBean> lista = new ArrayList<AuthorBean>();
		ResultSet rs = conexion
				.ejecutarServicio_SAS(" select b.nombre, b.correo from  [redSocial].[dbo].RsLikes a, [redSocial].[dbo].RsPersonas b  where a.id_publicacion = "
						+ id + " and b.id_persona= a.id_persona");
		while (rs.next()) {
			AuthorBean a = new AuthorBean(rs.getString("nombre"),
					"images/avatars/userpic.png",rs.getString("correo"));
			
			
			lista.add(a);
		}
		rs.close();
		return lista;
	}

	public void InsLikePublicacion(Integer id_pub, Integer id_persona)
			throws Exception {
		conexion.ejecutarInsertUpdate(" insert into [redSocial].[dbo].RsLikes values ((select ISNULL(max(id_like)+1,1) from [redSocial].[dbo].RsLikes ),"
				//+ id_pub + ",3)");
				+ id_pub + "," + id_persona + ")");
	}
	
	public void DelLikePublicacion(Integer id_pub, Integer id_persona)
			throws Exception {
		conexion.ejecutarInsertUpdate(" delete from [redSocial].[dbo].RsLikes where id_publicacion = "+id_pub+" and id_persona = "+ id_persona);
	}
	
	public void InsComentario(Integer id_pub, Integer id_persona, String comentario)  
			throws Exception {
		conexion.ejecutarInsertUpdate("insert into redSocial.dbo.RsComentarios(id_comentario, comentario, id_publicacion, id_persona) values ((select ISNULL(max(id_comentario)+1,1) from redSocial.dbo.RsComentarios),'"+ comentario +"',"+ id_pub +","+ id_persona +")");
	}
	
	public void InsPost(Integer id_persona, String Publicacion, String img, String tipo)  
			throws Exception { 
		conexion.ejecutarInsertUpdate("insert into redSocial.dbo.RsPublicaciones(id_publicacion, publicacion, id_persona, imagen, tipo) values ((select ISNULL(max(id_publicacion)+1,1) from redSocial.dbo.RsPublicaciones),'"+ Publicacion +"',"+ id_persona +",'"+img+"','"+tipo+"' )");
	}
	
	public void InsContact(List<ListaMed> contacto, Integer id_persona_amigo)  
			throws Exception { 
		for(int i=0; i < contacto.size() ; i++){
		conexion.ejecutarInsertUpdate("insert into redSocial.dbo.RsContactos(id_contacto, id_persona_contacto, fecha, estado, id_persona) values ((select ISNULL(max(id_contacto)+1,1) from redSocial.dbo.RsContactos),'"+(int)(contacto.get(i).getId_persona())+"',getdate(),'A','"+id_persona_amigo+"' )");//"+id_persona+"
		}
	}
	
	public List<AuthorBean> getContactos(Integer id) throws Exception {
		List<AuthorBean> lista = new ArrayList<AuthorBean>();
		ResultSet rs = conexion
				.ejecutarServicio_SAS(" select a.id_persona, a.nombre, a.imagen, a.correo from redSocial.dbo.RsPersonas a "
									 + " where id_persona not in (select b.id_persona_contacto from redSocial.dbo.RsContactos b " 
									 + " where b.id_persona = "+id+") and id_persona not in ("+id+") ");
		while (rs.next()) {
			AuthorBean a = new AuthorBean(rs.getString("nombre"),rs.getString("imagen"),rs.getString("correo"));
			a.setId_persona(rs.getInt("id_persona"));
			lista.add(a);
		}
		rs.close();
		return lista;
	}
	
	public List<AuthorBean> getAmigos(Integer id) throws Exception {
		List<AuthorBean> lista = new ArrayList<AuthorBean>();
		ResultSet rs = conexion
				.ejecutarServicio_SAS(" select a.id_persona, a.nombre, a.imagen, a.correo from redSocial.dbo.RsPersonas a "
									 + " where id_persona in (select b.id_persona_contacto from redSocial.dbo.RsContactos b " 
									 + " where b.id_persona = "+id+" and estado = 'A') ");
		while (rs.next()) {
			AuthorBean a = new AuthorBean(rs.getString("nombre"),rs.getString("imagen"),rs.getString("correo"));
			a.setId_persona(rs.getInt("id_persona"));
			lista.add(a);
		}
		rs.close();
		return lista;
	}
	
	public List<AuthorBean> getMedicos(Integer id) throws Exception {
		List<AuthorBean> lista = new ArrayList<AuthorBean>();
		ResultSet rs = conexion
				.ejecutarServicio_SAS(" select a.id_persona, a.nombre, a.imagen, a.correo from redSocial.dbo.RsPersonas a "
									 + " where id_persona in (select b.id_persona_contacto from redSocial.dbo.RsContactos b " 
									 + " where b.id_persona = "+id+") and rol = 1 ");
		while (rs.next()) {
			AuthorBean a = new AuthorBean(rs.getString("nombre"),rs.getString("imagen"),rs.getString("correo"));
			a.setId_persona(rs.getInt("id_persona"));
			lista.add(a);
		}
		rs.close();
		return lista;
	}
	
	public void InsUser(String usuario, String password,String nombre, Integer edad,String telefono,String correo, String img, String especialidad, String profesion)  
			throws Exception { 
		int rol;
		if (especialidad != null)
			rol = 1;
		else
			rol = 0;
		
		
		String passEncryp = StringEncrypt.encrypt(key, iv,password);
		conexion.ejecutarInsertUpdate("insert into redSocial.dbo.RsPersonas(id_persona,usuario,password,nombre,edad,telefono,correo,imagen,fechaCreacion,resetpass,estado,rol) values ((select ISNULL(max(id_persona)+1,1) from redSocial.dbo.RsPersonas),'"+ usuario +"','"+ passEncryp +"','"+ nombre +"','"+ edad +"','"+ telefono +"','"+ correo +"','"+img+"',getDate(),'S','A',"+rol+")");
		if (rol == 1)
			conexion.ejecutarInsertUpdate("insert into redSocial.dbo.RsDatosMedicos(id_persona,especialidad,profesion) values ((select ISNULL(max(id_persona),1) from redSocial.dbo.RsPersonas),'"+ especialidad +"','"+ profesion+"' )");
		}
	
	public void ActUser(String usuario, String nombre, Integer edad,String telefono,String correo, String img, String especialidad, String profesion)  
			throws Exception { 
		int rol;
		if (especialidad != null)
			rol = 1;
		else
			rol = 0;
		
		conexion.ejecutarInsertUpdate("update redSocial.dbo.RsPersonas set nombre = '"+ nombre +"' , edad = '"+ edad +"' , telefono = '"+ telefono +"' , correo = '"+ correo +"' , imagen = '"+img+"' , rol= "+rol+"  where usuario= '"+ usuario +"'");
		if (rol == 1)
			conexion.ejecutarInsertUpdate("update redSocial.dbo.RsDatosMedicos set especialidad ='"+ especialidad +"' ,profesion = '"+ profesion+"' where id_persona in (select id_persona from redSocial.dbo.RsPersonas where usuario= '"+ usuario +"')");
		}
	
	public void UpdatePass(String usuario, String password)  
			throws Exception { 
		String passEncryp = StringEncrypt.encrypt(key, iv,password);
		conexion.ejecutarInsertUpdate(" update redSocial.dbo.RsPersonas set password='"+passEncryp+"', resetpass='N' where usuario = '"+usuario+"'");
	}
	
	public void UpdatePerfil(int id, String imagen)  
			throws Exception { 
		conexion.ejecutarInsertUpdate(" update redSocial.dbo.RsPersonas set imagen='"+imagen+"' where id_persona = '"+id+"'");
	}
	
	public void bloquearFriend(int id, String nombre)  
			throws Exception { 
		conexion.ejecutarInsertUpdate("update redSocial.dbo.RsContactos set Estado='B' where id_persona = '"+id+"' and id_persona_contacto in (select id_persona from redSocial.dbo.RsPersonas where nombre = '"+ nombre +"' )");
	}
	
	public int GetNews(Integer id_publicacion) throws Exception { 
		ResultSet rs = conexion.ejecutarServicio_SAS("select count(*) from redSocial.dbo.RsPublicaciones where id_publicacion >"+id_publicacion+"");
		if(rs.next())
			return rs.getInt(1);
		return 1;
	}

}
