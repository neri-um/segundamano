package productos.aplicacion.puertos.entrada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManejadorEventos {
    @Autowired private IServicioProductos servicio;

    public void compraventaCreada(String idCompraventa, String idProducto) {
        try {
            servicio.marcarComoVendido(idProducto);
            System.out.println("[productos] Producto " + idProducto + " marcado como vendido");
        } catch (Exception e) {
            System.err.println("[productos] Error: " + e.getMessage());
        }
    }
}
