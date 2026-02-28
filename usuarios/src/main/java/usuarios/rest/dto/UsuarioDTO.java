package usuarios.rest.dto;

import usuarios.modelo.Usuario;

public class UsuarioDTO {
    private String id;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String fechaNacimiento;

    public UsuarioDTO(String id, String nombre, String apellidos,
                      String email, String telefono, String fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public static UsuarioDTO fromEntity(Usuario u) {
        return new UsuarioDTO(
            u.getId(),
            u.getNombre(),
            u.getApellidos(),
            u.getEmail(),
            u.getTelefono(),
            u.getFechaNacimiento().toString()
        );
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getFechaNacimiento() { return fechaNacimiento; }
}
