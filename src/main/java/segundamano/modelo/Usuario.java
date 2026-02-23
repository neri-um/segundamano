package segundamano.modelo;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
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

	public Usuario() {
		// JAXB
	}

	public Usuario(String email, String nombre, String apellidos, String clave, LocalDate fechaNacimiento,
			String telefono) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.clave = clave;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.id = UUID.randomUUID().toString();
		this.admin = false; // por defecto será un usuario normal, en serviciousuarios he puesto un método
							// para otorgar admin
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + ", clave="
				+ clave + ", fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono + ", admin=" + admin + "]";
	}

}
