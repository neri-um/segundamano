package segundamano.dto;

import java.time.LocalDateTime;

import segundamano.modelo.Categoria;
import segundamano.modelo.LugarRecogida;
import segundamano.modelo.Usuario;

public class ProductoDTO {
	private String id;
	private String titulo;
	private String descripcion;
	private double precio;
	private LocalDateTime fechaPublicacion;
	private Categoria categoria;
	private int visualizaciones = 0;
	private boolean envio;
	private LugarRecogida lugar;
	private Usuario vendedor;

	public ProductoDTO() {

	}

	public ProductoDTO(String titulo, String descripcion, double precio, Categoria categoria, boolean envio,
			Usuario vendedor) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.categoria = categoria;
		this.envio = envio;
		this.vendedor = vendedor;
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

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getVisualizaciones() {
		return visualizaciones;
	}

	public void setVisualizaciones(int visualizaciones) {
		this.visualizaciones = visualizaciones;
	}

	public boolean isEnvio() {
		return envio;
	}

	public void setEnvio(boolean envio) {
		this.envio = envio;
	}

	public LugarRecogida getLugar() {
		return lugar;
	}

	public void setLugar(LugarRecogida lugar) {
		this.lugar = lugar;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	@Override
	public String toString() {
		return "ProductoDTO [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", fechaPublicacion=" + fechaPublicacion + ", categoria=" + categoria + ", visualizaciones="
				+ visualizaciones + ", envio=" + envio + ", lugar=" + lugar + ", vendedor=" + vendedor + "]";
	}

	
}
