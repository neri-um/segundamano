package compraventas.aplicacion.puertos.salida;

import java.io.IOException;

public interface IPuertoProductos {
    String getIdVendedor(String idProducto);
    String getTitulo(String idProducto);
    double getPrecio(String idProducto);
    String getRecogida(String idProducto);
    
    void marcarComoVendido(String idProducto) throws IOException;
}