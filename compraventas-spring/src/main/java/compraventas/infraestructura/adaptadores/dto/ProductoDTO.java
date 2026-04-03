package compraventas.infraestructura.adaptadores.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoDTO {
    private String id;
    private String titulo;
    private double precio;
    private String recogida;     
    private VendedorDTO vendedor;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VendedorDTO {
        private String id;
        private String nombre;
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getRecogida() { return recogida; }
    public void setRecogida(String recogida) { this.recogida = recogida; }
    public VendedorDTO getVendedor() { return vendedor; }
    public void setVendedor(VendedorDTO vendedor) { this.vendedor = vendedor; }
}