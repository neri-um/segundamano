package productos.aplicacion.puertos.entrada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManejadorEventosImple implements ManejadorEventos {
    @Autowired private IServicioProductos servicio;

    public void compraventaCreada(String idCompraventa, String idProducto) {
        try {
            servicio.marcarComoVendido(idProducto);
            System.out.println("[productos] Producto " + idProducto + " marcado como vendido");
        } catch (Exception e) {
            System.err.println("[productos] Error: " + e.getMessage());
        }
    }

    @Override
    public void usuarioModificado(String idUsuario, String nombre,
                                   String apellidos, String email) {
        try {
            servicio.actualizarUsuarioSimplificado(idUsuario, nombre, apellidos, email);
        } catch (Exception e) {
            System.err.println("[productos] Error actualizando usuario: " + e.getMessage());
        }
    }
}
