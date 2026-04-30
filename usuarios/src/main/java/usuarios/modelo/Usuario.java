package usuarios.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name = "usuarios")
public class Usuario implements Identificable {
	@Id
	private String id;
	private String email;
	private String nombre;
	private String apellidos;
	private String clave;
	private LocalDate fechaNacimiento;
	private String telefono;
	private boolean admin;
	private int contadorVentas = 0;
	private int contadorCompras = 0;
	private String githubLogin;

	
	public Usuario() {
		// JPA
	}

	public Usuario(String email, String nombre, String apellidos, String clave, LocalDate fechaNacimiento,
			String telefono) {
		this.id = java.util.UUID.randomUUID().toString();
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.clave = clave;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.admin = false;
	}

	// --> GETTERS
	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getClave() {
		return clave;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public boolean isAdmin() {
		return admin;
	}

	// --> SETTERS
	public void setEmail(String email) {
		this.email = email;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public int getContadorVentas() {
		return contadorVentas;
	}

	public void setContadorVentas(int contadorVentas) {
		this.contadorVentas = contadorVentas;
	}

	public int getContadorCompras() {
		return contadorCompras;
	}

	public void setContadorCompras(int contadorCompras) {
		this.contadorCompras = contadorCompras;
	}

	public String getGithubLogin() { return githubLogin; }
	public void setGithubLogin(String githubLogin) { this.githubLogin = githubLogin; }
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + ", clave="
				+ clave + ", fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono + ", admin=" + admin + "]";
	}
}