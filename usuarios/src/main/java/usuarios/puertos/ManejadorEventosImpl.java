package usuarios.puertos;

import servicio.FactoriaServicios;
import usuarios.servicio.IServicioUsuarios;


public class ManejadorEventosImpl implements ManejadorEventos {

    private IServicioUsuarios servicio =
        FactoriaServicios.getServicio(IServicioUsuarios.class);

    @Override
    public void compraventaCreada(String idCompraventa, String idVendedor,
            String idComprador, String idProducto) {
        try {
            servicio.incrementarVentas(idVendedor);
            servicio.incrementarCompras(idComprador);
        } catch (Exception e) {
            System.err.println("Error procesando compraventa-creada: " + e.getMessage());
        }
    }
}