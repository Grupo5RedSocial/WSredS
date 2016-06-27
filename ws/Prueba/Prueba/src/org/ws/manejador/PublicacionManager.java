package org.ws.manejador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ws.util.conexion.ConexionBase;
import org.ws.accesoDB.PostBeanDB;
import org.ws.util.entidad.AuthorBean;
import org.ws.util.entidad.ContactBlock;
import org.ws.util.entidad.ListaMed;
import org.ws.util.entidad.PostBean;
import org.ws.util.entidad.User;
import org.ws.util.entidad.Usuario;
import org.zkoss.web.fn.ServletFns;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PublicacionManager {

	public final Gson g = new Gson();
	public final PostBeanDB publicacionesAB = new PostBeanDB();
	private AuthorBean currentUser;

	/**
	 * Currently logged in user
	 */
	public AuthorBean getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(AuthorBean currentUser) {
		this.currentUser = currentUser;
	}

	public String getPublicacion(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			publicacionesAB.setConexion(c);
			List<PostBean> lista = publicacionesAB.listaPost(Integer.parseInt(json));
			respuesta = g.toJson(lista);
		} catch (Exception e) {
			System.out.println(e);
		}
		return respuesta;
	}
	
	public String getContactos(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			
			publicacionesAB.setConexion(c);
			List<AuthorBean> lista = publicacionesAB.getContactos(Integer.parseInt(json));
			respuesta = g.toJson(lista);
		} catch (Exception e) {
			System.out.println(e);
		}
		return respuesta;
	}
	
	public String getMedicos(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			publicacionesAB.setConexion(c);
			List<AuthorBean> lista = publicacionesAB.getMedicos(Integer.parseInt(json));
			respuesta = g.toJson(lista);
		} catch (Exception e) {
			System.out.println(e);
		}
		return respuesta;
	}
	
	public String getfriends(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			publicacionesAB.setConexion(c);
			List<AuthorBean> lista = publicacionesAB.getAmigos(Integer.parseInt(json));
			respuesta = g.toJson(lista);
		} catch (Exception e) {
			System.out.println(e);
		}
		return respuesta;
	}
	
	
	

	public String Addcontacto(String json) {
		String respuesta = null;

		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			java.lang.reflect.Type listType = new TypeToken<List<ListaMed>>(){}.getType();
			List<ListaMed> contactos= g.fromJson(json, listType);
			publicacionesAB.setConexion(c);
			publicacionesAB.InsContact(contactos, contactos.get(0).getCurrentUser());
			respuesta = "OK";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al agregar contacto";
		}
		return respuesta;
	}
	
	public String guardarPublicacion(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			PostBean publicacion=g.fromJson(json, PostBean.class);
			publicacionesAB.setConexion(c);
			publicacionesAB.InsLikePublicacion(publicacion.getId_pub(), publicacion.getAuthor().getId_persona());
			respuesta = "OK";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al guardar la publicacion";
		}
		return respuesta;
	}
	
	public String EliminarLike(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			PostBean publicacion=g.fromJson(json, PostBean.class);
			publicacionesAB.setConexion(c);
			publicacionesAB.DelLikePublicacion(publicacion.getId_pub(), publicacion.getAuthor().getId_persona());
			respuesta = "Se elimino con exito";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al eliminar el like";
		}
		return respuesta;
	}
	
	public String AddComentario(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			PostBean publicacion=g.fromJson(json, PostBean.class);
			publicacionesAB.setConexion(c);
			publicacionesAB.InsComentario(publicacion.getId_pub(),  publicacion.getAuthor().getId_persona(), publicacion.getContent());
			respuesta = "OK";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al agregar el comentario";
		}
		return respuesta;
	}
	
	public String AddPublicacion(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			PostBean publicacion=g.fromJson(json, PostBean.class);
			publicacionesAB.setConexion(c);
			publicacionesAB.InsPost(publicacion.getAuthor().getId_persona(), publicacion.getContent(), publicacion.getRutaImg(), publicacion.getTipo());
			respuesta = "OK";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al agregar la publicacion";
		}
		return respuesta;
	}
	
	public String AddUsuario(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			User usuario=g.fromJson(json, User.class);
			publicacionesAB.setConexion(c);
			publicacionesAB.InsUser(usuario.getUserName(), usuario.getPassword(), usuario.getNombre(),usuario.getAge() ,usuario.getTelephone(), usuario.getEmail(), usuario.getImagen(), usuario.getEspecialidad(), usuario.getProfesion());
			respuesta = "OK";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al insertar el usuario";
		}
		return respuesta;
	}
	
	public String EditUsuario(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			User usuario=g.fromJson(json, User.class);
			publicacionesAB.setConexion(c);
			publicacionesAB.ActUser(usuario.getUserName(), usuario.getNombre(),usuario.getAge() ,usuario.getTelephone(), usuario.getEmail(), usuario.getImagen(), usuario.getEspecialidad(), usuario.getProfesion());
			respuesta = "OK";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al actualizar los datos del usuario";
		}
		return respuesta;
	}
	
	public String changePass(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			User usuario=g.fromJson(json, User.class);
			publicacionesAB.setConexion(c);
			publicacionesAB.UpdatePass(usuario.getUserName(), usuario.getPassword());
			respuesta = "OK";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al realizar el cambio de cotraseña";
		}
		return respuesta;
	}
	
	public String actPerfil(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			AuthorBean usuario=g.fromJson(json, AuthorBean.class);
			publicacionesAB.setConexion(c);
			publicacionesAB.UpdatePerfil(usuario.getId_persona(), usuario.getAvatar());
			respuesta = "OK";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al actualizar la imagen de perfil";
		}
		return respuesta;
	}
	
	public String blockAmigo(String json) {
		String respuesta = null;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			ContactBlock usuario=g.fromJson(json, ContactBlock.class);
			publicacionesAB.setConexion(c);
			publicacionesAB.bloquearFriend(usuario.getId(), usuario.getName());
			respuesta = "Se inserto con exito";
		} catch (Exception e) {
			System.out.println(e);
			respuesta = "Ocurrio un error al insertar el usuario";
		}
		return respuesta;
	}
	
	public int getNewsFeed(String json) {
		int respuesta = 0;
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			PostBean publicacion=g.fromJson(json, PostBean.class);
			publicacionesAB.setConexion(c);
			respuesta = publicacionesAB.GetNews(publicacion.getId_pub());
		} catch (Exception e) {
			System.out.println(e);
			respuesta = -1;
		}
		return respuesta;
	}
}
