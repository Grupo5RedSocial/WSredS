package org.ws.manejador;
import javax.jms.Session;
import javax.ws.rs.core.Response;

import org.jboss.samples.rs.webservices.ResponseManager;
import org.ws.util.conexion.ConexionBase;
import org.ws.accesoDB.LoginDB;
import org.ws.util.entidad.Respuesta;
import org.ws.util.entidad.Usuario;

import com.google.gson.Gson;

public class LoginManager {

	public final Gson g = new Gson();
	private final LoginDB logindb=new LoginDB();
	
	public Response login(String json){
		Respuesta res=new Respuesta();
		try {
			ConexionBase c = new ConexionBase();
			c.Conectar();
			logindb.setConexion(c);
			Usuario usu=g.fromJson(json, Usuario.class);
			Usuario usuario=logindb.getUsuario(usu);
			if(usuario==null){
				res.setCodigo("0");
				res.setMensaje("Usuario no existe");
				return ResponseManager.ResponseHeader(g.toJson(res),null);
			}
			if(!usu.getPassword().equals(usuario.getPassword())){
				res.setCodigo("0");
				res.setMensaje("Contraseña erronea");
				return ResponseManager.ResponseHeader(g.toJson(res),null);
			}
			res.setData(usuario);
			return ResponseManager.ResponseHeader(g.toJson(res));
		} catch (Exception e) {
			System.out.println(e);
			res.setCodigo("0");
			res.setMensaje("Ha ocurrido un error");
			return ResponseManager.ResponseHeader(g.toJson(res),null);
		}
	}
}
