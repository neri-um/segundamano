package productos.infraestructura.adaptadores.entrada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import productos.aplicacion.puertos.entrada.IServicioProductos;
import productos.aplicacion.puertos.entrada.ManejadorEventos;

@Component
public class ManejadorEventosImpl implements ManejadorEventos {

    @Autowired 
    private IServicioProductos servicio;

    @Override
    public void compraventaCreada(String idCompraventa, String idProducto) {
        try {
            servicio.marcarComoVendido(idProducto);
            System.out.println("[productos] Producto " + idProducto + " marcado como vendido");
        } catch (Exception e) {
            System.err.println("[productos] Error: " + e.getMessage());
        }
    }
}