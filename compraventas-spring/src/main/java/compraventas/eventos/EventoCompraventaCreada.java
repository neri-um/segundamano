package compraventas.eventos;

public class EventoCompraventaCreada extends Evento {
	private String idVendedor;
	private String idComprador;
	private String idProducto;

	public EventoCompraventaCreada() {}
	
	public EventoCompraventaCreada(String id, String idVendedor, String idComprador, String idProducto) {
		super(id, "compraventa-creada");
		this.idVendedor = idVendedor;
		this.idComprador = idComprador;
		this.idProducto = idProducto;
	}

	public String getIdVendedor() {
		return idVendedor;
	}

	public String getIdComprador() {
		return idComprador;
	}

	public String getIdProducto() {
		return idProducto;
	}
}