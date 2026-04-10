package compraventas.aplicacion.puertos.entrada;


public interface ManejadorEventos {
    void usuarioModificado(String idUsuario, String nombre, String apellidos);
}