package org.ws.accesoDB;

import java.sql.ResultSet;

import org.ws.util.conexion.ConexionBase;
import org.ws.util.StringEncrypt;
import org.ws.util.entidad.Usuario;

public class LoginDB {

	private ConexionBase conexion;
	private String key = "92AE31A79FEEB2A3"; //llave
	private String iv = "0123456789ABCDEF"; // vector de inicialización
	private StringEncrypt encrypt;
	public void setConexion(ConexionBase conexion) {
		this.conexion = conexion;
	}
	
	public Usuario getUsuario(Usuario usu) throws Exception{
		Usuario usuario=null;
		ResultSet rs = conexion
				.ejecutarServicio_SAS("SELECT [usuario],[password],[id_persona] FROM [redSocial].[dbo].[RsPersonas] a where a.usuario = '"+usu.getUsuario()+"'");
		if (rs.next()) {
			usuario = new Usuario();
			String pass = StringEncrypt.decrypt(key, iv, rs.getString("password"));
			usuario.setPassword(pass);
			usuario.setUsuario(rs.getString("usuario"));
			PostBeanDB postbean=new PostBeanDB();
			postbean.setConexion(conexion);
			usuario.setUsuarioLogin(postbean.getPersona(rs.getInt("id_persona")));
		}
		rs.close();
		return usuario;
	}
}
