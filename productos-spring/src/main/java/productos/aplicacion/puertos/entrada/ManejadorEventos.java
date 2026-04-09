package productos.aplicacion.puertos.entrada;

public interface ManejadorEventos {
    void compraventaCreada(String idCompraventa, String idProducto);
}