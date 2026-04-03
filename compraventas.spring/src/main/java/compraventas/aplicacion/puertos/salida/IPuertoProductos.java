package compraventas.aplicacion.puertos.salida;

public interface IPuertoProductos {
    String getIdVendedor(String idProducto);
    String getTitulo(String idProducto);
    double getPrecio(String idProducto);
    String getRecogida(String idProducto);
}