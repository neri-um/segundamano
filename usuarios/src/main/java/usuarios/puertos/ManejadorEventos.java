package usuarios.puertos;

//de entrada
public interface ManejadorEventos {
	void compraventaCreada(String idCompraventa, String idVendedor, String idComprador, String idProducto);
}