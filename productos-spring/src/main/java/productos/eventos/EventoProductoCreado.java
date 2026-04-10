package productos.eventos;

public class EventoProductoCreado extends Evento {

    private String idVendedor;
    private String titulo;
    private double precio;

    public EventoProductoCreado(String idProducto, String idVendedor,
                                 String titulo, double precio) {
        super(idProducto, "producto-creado");
        this.idVendedor = idVendedor;
        this.titulo = titulo;
        this.precio = precio;
    }

    public String getIdVendedor() { return idVendedor; }
    public String getTitulo()     { return titulo; }
    public double getPrecio()     { return precio; }
}