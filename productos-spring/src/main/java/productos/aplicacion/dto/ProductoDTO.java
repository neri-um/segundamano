package productos.aplicacion.dto;

import java.time.LocalDateTime;


import productos.dominio.modelo.Producto;

public class ProductoDTO {
	private String id;
    private String titulo;
    private String descripcion;
    private double precio;
    private String estado;
    private LocalDateTime fechaPublicacion;
    private String categoria;
    private int visualizaciones;
    private boolean envio;
    private String lugarRecogida;
    private double longitud;
    private double latitud;
    private String usuarioVendedor;
    private String idVendedor;
    private boolean vendido;
    
	public ProductoDTO() {
		//para el fromEntity linea 1
	}
    
	public static ProductoDTO fromEntity(Producto p) {
	    ProductoDTO dto = new ProductoDTO();
	    dto.id = p.getId();
	    dto.titulo = p.getTitulo();
	    dto.descripcion = p.getDescripcion();
	    dto.precio = p.getPrecio();
	    dto.estado = p.getEstado().toString();
	    dto.fechaPublicacion = p.getFechaPublicacion();
	    dto.categoria = p.getCategoria().getNombre(); //para que se vea bonito
	    dto.visualizaciones = p.getVisualizaciones();
	    dto.envio = p.isEnvio();
	    dto.vendido = p.isVendido();

	    if (p.getLugar() != null) {
	        dto.lugarRecogida = p.getLugar().getDescripcion();
	        dto.longitud = p.getLugar().getLongitud();
	        dto.latitud = p.getLugar().getLatitud();
	    }

	    dto.usuarioVendedor = p.getVendedor() != null ? p.getVendedor().getNombre() : null;
	    dto.idVendedor      = p.getVendedor() != null ? p.getVendedor().getId()     : null; 
	    return dto;
	}

	public String getId() {
	    return id;
	}

	public void setId(String id) {
	    this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getVisualizaciones() { 
		return visualizaciones; 
	}
	
	public void setVisualizaciones(int v) { 
		this.visualizaciones = v; 
	}
	
	public boolean isEnvio() {
		return envio;
	}

	public void setEnvio(boolean envio) {
		this.envio = envio;
	}

	public String getLugarRecogida() {
		return lugarRecogida;
	}

	public void setLugarRecogida(String lugarRecogida) {
		this.lugarRecogida = lugarRecogida;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public String getUsuarioVendedor() {
		return usuarioVendedor;
	}

	public void setUsuarioVendedor(String usuarioVendedor) {
		this.usuarioVendedor = usuarioVendedor;
	}
	
	public String getIdVendedor() { 
		return idVendedor; 
	}
	
	public void setIdVendedor(String idVendedor) { 
		this.idVendedor = idVendedor; 
	}

	public boolean isVendido() {
		return vendido;
	}

	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}
	
}
