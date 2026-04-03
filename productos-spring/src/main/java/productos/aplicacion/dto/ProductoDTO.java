package productos.aplicacion.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import productos.dominio.modelo.Producto;

public class ProductoDTO {
    private String titulo;
    private String descripcion;
    private double precio;
    private String estado;
    private LocalDateTime fechaPublicacion;
    private String categoria;  
    private boolean envio;
    private String lugarRecogida;
    private double longitud;
    private double latitud;
    private String usuarioVendedor;
    
	public ProductoDTO() {
		//para el fromEntity linea 1
	}
    
	public static ProductoDTO fromEntity(Producto p) {
		ProductoDTO dto = new ProductoDTO();
		dto.titulo = p.getTitulo();
		dto.descripcion = p.getDescripcion();
		dto.precio = p.getPrecio();
		dto.estado = p.getEstado().toString();
		dto.fechaPublicacion = p.getFechaPublicacion();
		dto.categoria = p.getCategoria().toString();
		dto.envio = p.isEnvio();
		dto.lugarRecogida = p.getLugar().getDescripcion();
		dto.longitud = p.getLugar().getLongitud();
		dto.latitud = p.getLugar().getLatitud();
		dto.usuarioVendedor = p.getVendedor().getNombre();
		return dto;
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
	
    
}
