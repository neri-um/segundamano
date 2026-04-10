package usuarios.adaptadores;

import servicio.FactoriaServicios;
import usuarios.puertos.ManejadorEventos;
import usuarios.servicio.IServicioUsuarios;

public class ManejadorEventosImpl implements ManejadorEventos {

	 private IServicioUsuarios servicio;
	 
	    private IServicioUsuarios getServicio() {
	        if (servicio == null)
	            servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);
	        return servicio;
	    }

	@Override
	public void compraventaCreada(String idCompraventa, String idVendedor, String idComprador, String idProducto) {
		try {
			 getServicio().incrementarVentas(idVendedor);
			 getServicio().incrementarCompras(idComprador);
		} catch (Exception e) {
			System.err.println("Error procesando compraventa-creada: " + e.getMessage());
		}
	}
}