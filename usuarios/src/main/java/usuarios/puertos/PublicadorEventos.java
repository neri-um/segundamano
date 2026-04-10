package usuarios.puertos;

public interface PublicadorEventos {
 void usuarioCreado(String idUsuario, String nombre, String apellidos, String email);
 void usuarioModificado(String idUsuario, String nombre, String apellidos, String email);
}
