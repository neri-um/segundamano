package productos.servicio.test;

import productos.modelo.*;
import productos.servicio.IServicioProductos;
import servicio.FactoriaServicios;

public class PruebaProductos {

    public static void main(String[] args) throws Exception {

        IServicioProductos servicio = FactoriaServicios.getServicio(IServicioProductos.class);

        // Usar un UsuarioSimplificado ya existente en la BD
        UsuarioSimplificado vendedor = new UsuarioSimplificado(
            "usr-001", "juan@example.com", "Juan", "García López");

        // Alta de producto
        String idProducto = servicio.altaProducto(
            "Bicicleta de montaña",
            "Bicicleta en buen estado, poco uso",
            150.0,
            Estado.BUEN_ESTADO,
            null,  // categoría (previamente cargada)
            true,
            vendedor
        );

        System.out.println("Producto creado con ID: " + idProducto);
        System.out.println("fin.");
    }
}