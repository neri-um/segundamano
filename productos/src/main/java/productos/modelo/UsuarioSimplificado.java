package productos.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name = "usuarios_simplificados")
public class UsuarioSimplificado implements Identificable {

    @Id
    private String id;        // mismo ID que en el microservicio Usuarios
    private String email;
    private String nombre;
    private String apellidos;

    public UsuarioSimplificado() {
        // JPA
    }

    public UsuarioSimplificado(String id, String email, String nombre, String apellidos) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // GETTERS
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }

    // SETTERS
    @Override
    public void setId(String id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    @Override
    public String toString() {
        return "UsuarioSimplificado [id=" + id + ", email=" + email 
               + ", nombre=" + nombre + ", apellidos=" + apellidos + "]";
    }
}