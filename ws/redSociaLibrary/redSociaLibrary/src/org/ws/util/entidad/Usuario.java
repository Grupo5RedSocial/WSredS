package org.ws.util.entidad;

public class Usuario {

	private Integer idUsuario;
	private String usuario;
	private String password;
	private AuthorBean usuarioLogin;
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AuthorBean getUsuarioLogin() {
		return usuarioLogin;
	}
	public void setUsuarioLogin(AuthorBean usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}
	
}
