package productos.eventos;

public class EventoProductoModificado extends Evento {

    private String titulo;
    private double precio;

    public EventoProductoModificado(String idProducto, String titulo, double precio) {
        super(idProducto, "producto-modificado");
        this.titulo = titulo;
        this.precio = precio;
    }

    public String getTitulo() { return titulo; }
    public double getPrecio() { return precio; }
}