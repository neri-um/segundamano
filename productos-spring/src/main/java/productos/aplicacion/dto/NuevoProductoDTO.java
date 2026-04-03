package productos.aplicacion.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NuevoProductoDTO {

	@NotBlank
	private String titulo;

	@NotBlank
	private String descripcion;

	@Positive
	private double precio;

	@NotBlank
	private String estado;

	@NotNull
	private LocalDateTime fechaPublicacion;

	@NotBlank
	private String categoria;

	private boolean envio;

	@NotBlank
	private String lugarRecogida;

	private double longitud;
	private double latitud;

	@NotBlank
	private String idVendedor;
	
	private String vendedorId;
	
	public NuevoProductoDTO() {
		//para leer el json
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

	public String getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(String idVendedor) {
		this.idVendedor = idVendedor;
	}

	public String getVendedorId() {
		return vendedorId;
	}

	public void setVendedorId(String vendedorId) {
		this.vendedorId = vendedorId;
	}  
    
	
    
}
