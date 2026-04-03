package productos.dominio.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios_simplificados")
public class UsuarioSimplificado{

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
    public void setId(String id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    @Override
    public UsuarioSimplificado obtenerUsuario(String token) throws Exception {
        // Extraer userId del JWT (sin librería, solo Base64 estándar Java)
        String payload = token.replace("Bearer ", "").trim().split("\\.")[1];
        String json = new String(Base64.getUrlDecoder().decode(payload));
        String userId = new ObjectMapper().readTree(json).get("sub").asText();

        // Llamar al endpoint público de usuarios (sin token)
        ResponseEntity<UsuarioRemotoDTO> resp = restTemplate.getForEntity(
            "http://localhost:8080/usuarios/api/usuarios/" + userId + "/publico",
            UsuarioRemotoDTO.class
        );

        UsuarioRemotoDTO dto = resp.getBody();
        if (dto == null) throw new Exception("Usuario no encontrado: " + userId);
        return new UsuarioSimplificado(dto.getId(), dto.getEmail(), dto.getNombre(), dto.getApellidos());
    }
}