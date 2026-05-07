package usuarios.rest.dto;

import usuarios.modelo.Usuario;

public class UsuarioDTO {
    private String id;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String fechaNacimiento;
    private String githubLogin;

    public UsuarioDTO(String id, String nombre, String apellidos,
                      String email, String telefono, String fechaNacimiento,
                      String githubLogin) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.githubLogin = githubLogin;
    }

    public static UsuarioDTO fromEntity(Usuario u) {
        return new UsuarioDTO(
            u.getId(),
            u.getNombre(),
            u.getApellidos(),
            u.getEmail(),
            u.getTelefono(),
            u.getFechaNacimiento().toString(),
            u.getGithubLogin()
        );
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getGithubLogin() { return githubLogin; }
}
