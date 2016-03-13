package org.ws.util.entidad;

// Represent poster of news or commenter on posts
public class AuthorBean {
	private String name;
	private String avatar;
	private String email;
	private Integer id_persona;

	public AuthorBean() {
	}
	
	public AuthorBean(String name, String avatar, String email) {
		this.name = name;
		this.avatar = avatar;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId_persona() {
		return id_persona;
	}

	public void setId_persona(Integer id_persona) {
		this.id_persona = id_persona;
	}

}
