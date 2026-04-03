package compraventas.dominio.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "compraventas")
public class Compraventa {

    @Id
    private String id;
    private String idProducto;
    private String titulo;
    private double precio;
    private String recogida;
    private String idVendedor;
    private String nombreVendedor;
    private String idComprador;
    private String nombreComprador;
    private LocalDateTime fecha;

    public Compraventa() {}

    public Compraventa(String idProducto, String titulo, double precio, String recogida,
                       String idVendedor, String nombreVendedor,
                       String idComprador, String nombreComprador) {
        this.id = UUID.randomUUID().toString();
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.precio = precio;
        this.recogida = recogida;
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
        this.idComprador = idComprador;
        this.nombreComprador = nombreComprador;
        this.fecha = LocalDateTime.now();
    }

    // Getters y setters
    public String getId() { return id; }
    public String getIdProducto() { return idProducto; }
    public String getTitulo() { return titulo; }
    public double getPrecio() { return precio; }
    public String getRecogida() { return recogida; }
    public String getIdVendedor() { return idVendedor; }
    public String getNombreVendedor() { return nombreVendedor; }
    public String getIdComprador() { return idComprador; }
    public String getNombreComprador() { return nombreComprador; }
    public LocalDateTime getFecha() { return fecha; }

    public void setId(String id) { this.id = id; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setRecogida(String recogida) { this.recogida = recogida; }
    public void setIdVendedor(String idVendedor) { this.idVendedor = idVendedor; }
    public void setNombreVendedor(String nombreVendedor) { this.nombreVendedor = nombreVendedor; }
    public void setIdComprador(String idComprador) { this.idComprador = idComprador; }
    public void setNombreComprador(String nombreComprador) { this.nombreComprador = nombreComprador; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}