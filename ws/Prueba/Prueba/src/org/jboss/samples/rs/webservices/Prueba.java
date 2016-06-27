package org.jboss.samples.rs.webservices;

import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.ws.manejador.LoginManager;
import org.ws.manejador.PublicacionManager;

import com.google.gson.Gson;

import javax.ws.rs.QueryParam;

@Path("/WsRed")
public class Prueba {

	private Random RANDOM = new Random();
	private Gson g = new Gson();

	// private static List<PostBean> posts = null;

/*	@GET()
	@Produces("application/json")
	public Response sayHello() {

		PublicacionManager publicaciones = new PublicacionManager();
		return ResponseManager.ResponseHeader(publicaciones.getPublicacion(""));

	}*/
	
	@Path("getPublicaciones")
	@GET()
	public Response sayHello(@QueryParam("id") String json) {

		PublicacionManager publicaciones = new PublicacionManager();
		return ResponseManager.ResponseHeader(publicaciones.getPublicacion(json));

	}

	
	@Path("guardarLike")
	@POST()
	public Response guardarDatos(String parametro) {

		PublicacionManager publicaciones = new PublicacionManager();

		return ResponseManager.ResponseHeader(publicaciones.guardarPublicacion(parametro));

	}
	
	@Path("eliminarLike")
	@POST()
	public Response eliminarDatos(String parametro) {

		PublicacionManager publicaciones = new PublicacionManager();
		return ResponseManager.ResponseHeader(publicaciones.EliminarLike(parametro));

	}
	
	@Path("addComentario")
	@POST()
	public Response AddComment(String parametro) {

		PublicacionManager publicaciones = new PublicacionManager();

		return ResponseManager.ResponseHeader(publicaciones.AddComentario(parametro));

	}
	
	@Path("addPost")
	@POST()
	public Response AddPost(String parametro) {

		PublicacionManager publicaciones = new PublicacionManager();
		return ResponseManager.ResponseHeader(publicaciones.AddPublicacion(parametro));
	}
	
	@Path("addContact")
	@POST()
	public Response AddContactos(String parametro) {

		PublicacionManager publicaciones = new PublicacionManager();
		return ResponseManager.ResponseHeader(publicaciones.Addcontacto(parametro));

	}
	
	@Path("getContacts")
	//@Consumes("application/json")
	@GET()
	public Response GetContacts(@QueryParam("id") String json) {
		PublicacionManager publicaciones = new PublicacionManager();
		return ResponseManager.ResponseHeader(publicaciones.getContactos(json));
	}
	
	@Path("getDr")
	//@Consumes("application/json")
	@GET()
	public Response GetMedicos(@QueryParam("id") String json) {
		PublicacionManager publicaciones = new PublicacionManager();
		return ResponseManager.ResponseHeader(publicaciones.getMedicos(json));
	}
	
	@Path("getAmigos")
	//@Consumes("application/json")
	@GET()
	public Response GetAmigos(@QueryParam("id") String json) {

		PublicacionManager publicaciones = new PublicacionManager();
		return ResponseManager.ResponseHeader(publicaciones.getfriends(json));
	}
	
	@Path("getNewsFeed")
	@POST()
	public int GetFeed(String json) {
		PublicacionManager publicaciones = new PublicacionManager();
		return publicaciones.getNewsFeed(json);

	}

	@Path("login")
	@POST()
	public Response login(String json){
		LoginManager login=new LoginManager();
		return login.login(json);
	}
	
	@Path("addUser")
	@POST()
	public Response AddUser(String parametro) {

		System.out.println("AddUser->"+parametro);
		PublicacionManager usuarios = new PublicacionManager();

		return ResponseManager.ResponseHeader(usuarios.AddUsuario(parametro));

	}
	
	@Path("editUser")
	@POST()
	public Response EditUser(String parametro) {

		PublicacionManager usuarios = new PublicacionManager();

		return ResponseManager.ResponseHeader(usuarios.EditUsuario(parametro));

	}
	
	@Path("changePass")
	@POST()
	public Response UpdatePass(String parametro) {

		PublicacionManager usuarios = new PublicacionManager();

		return ResponseManager.ResponseHeader(usuarios.changePass(parametro));

	}
	
	@Path("updatePerfil")
	@POST()
	public Response UpdatePerfil(String parametro) {

		PublicacionManager usuarios = new PublicacionManager();

		return ResponseManager.ResponseHeader(usuarios.actPerfil(parametro));

	}
	
	@Path("bloquearAmigo")
	@POST()
	public Response BlokFriend(String parametro) {

		PublicacionManager usuarios = new PublicacionManager();

		return ResponseManager.ResponseHeader(usuarios.blockAmigo(parametro));

	}
	
}
