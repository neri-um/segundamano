package productos.aplicacion.puertos.entrada;

public interface ManejadorEventos {
    void compraventaCreada(String idCompraventa, String idProducto);
    void usuarioModificado(String idUsuario, String nombre, String apellidos, String email);
}