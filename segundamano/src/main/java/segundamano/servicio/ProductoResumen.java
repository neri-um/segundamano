package segundamano.servicio;

import java.time.LocalDateTime;

import segundamano.modelo.Categoria;

public class ProductoResumen {
	private String id;
	private String titulo;
	private double precio;
	private LocalDateTime fechaPublicacion;
	private Categoria categoria;
	private int visualizaciones = 0;

	public ProductoResumen() {
		// JAXB
	}

	public ProductoResumen(String id, String titulo, double precio, LocalDateTime fechaPublicacion, Categoria categoria,
			int visualizaciones) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.precio = precio;
		this.fechaPublicacion = fechaPublicacion;
		this.categoria = categoria;
		this.visualizaciones = visualizaciones;
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

}
