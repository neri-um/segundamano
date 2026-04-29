package compraventas.aplicacion.puertos.salida;

public class DatosProducto {

    private final String idVendedor;
    private final String titulo;
    private final double precio;
    private final String recogida;
    private final boolean vendido;

    public DatosProducto(String idVendedor, String titulo, double precio,
                         String recogida, boolean vendido) {
        this.idVendedor = idVendedor;
        this.titulo     = titulo;
        this.precio     = precio;
        this.recogida   = recogida;
        this.vendido    = vendido;
    }

    public String getIdVendedor() { return idVendedor; }
    public String getTitulo()     { return titulo; }
    public double getPrecio()     { return precio; }
    public String getRecogida()   { return recogida; }
    public boolean isVendido()    { return vendido; }
}