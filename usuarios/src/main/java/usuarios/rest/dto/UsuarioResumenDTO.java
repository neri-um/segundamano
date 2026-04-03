package usuarios.rest.dto;

import usuarios.modelo.Usuario;

public class UsuarioResumenDTO {
	private String id;
	private String nombre;
    private String apellidos;  
	private String email;

	public UsuarioResumenDTO() {
	}

	public UsuarioResumenDTO(String id, String nombre, String apellidos, String email) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos; 
		this.email = email;
	}

	public static UsuarioResumenDTO fromEntity(Usuario u) {
		return new UsuarioResumenDTO(u.getId(), u.getNombre(),u.getApellidos(), u.getEmail());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    public String getApellidos() { 
    	return apellidos; 
    }
    
    public void setApellidos(String apellidos) { 
    	this.apellidos = apellidos; 
    }
}
