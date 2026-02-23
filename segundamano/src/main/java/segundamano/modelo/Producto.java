package segundamano.modelo;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity // JPA
@Table(name = "productos")
public class Producto implements Identificable {
	@Id
	private String id;
	private String titulo;
	@Lob
	private String descripcion;
	private double precio;
	@Enumerated(EnumType.STRING)
	private Estado estado;
	private LocalDateTime fechaPublicacion;
	@OneToOne
	private Categoria categoria;
	private int visualizaciones = 0;
	private boolean envio;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "lugar_recogida_id")
	private LugarRecogida lugar;
	private Usuario vendedor;

	public Producto() {
		// JAXB
	}

	public Producto(String titulo, String descripcion, double precio, Estado estado, Categoria categoria, boolean envio,
			Usuario vendedor) {
		this.visualizaciones = 0;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.estado = estado;
		this.categoria = categoria;
		this.envio = envio;
		this.vendedor = vendedor;
	}

	// --> GETTERS
	public String getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public Estado getEstado() {
		return estado;
	}

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public int getVisualizaciones() {
		return visualizaciones;
	}

	public boolean isEnvio() {
		return envio;
	}

	public LugarRecogida getLugar() {
		return lugar;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	// --> SETTERS
	public void setId(String id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setPrecio(double nuevoPrecio) {
		this.precio = nuevoPrecio;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setVisualizaciones(int visualizaciones) {
		this.visualizaciones = visualizaciones;
	}

	public void setEnvio(boolean envio) {
		this.envio = envio;
	}

	public void setLugar(LugarRecogida lugar) {
		this.lugar = lugar;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", estado=" + estado + ", fechaPublicacion=" + fechaPublicacion + ", categoria=" + categoria
				+ ", visualizaciones=" + visualizaciones + ", envio=" + envio + ", lugar=" + lugar + ", vendedor="
				+ vendedor + "]";
	}

}
