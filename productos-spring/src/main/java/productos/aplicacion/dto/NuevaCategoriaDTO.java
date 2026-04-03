package productos.aplicacion.dto;

import javax.validation.constraints.NotBlank;

public class NuevaCategoriaDTO {

	@NotBlank
	private String nombre;

	private String descripcion;

	@NotBlank
	private String ruta;

	private String padre; // null si es categoría raíz

	public NuevaCategoriaDTO() {
		// para leer el json
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getPadre() {
		return padre;
	}

	public void setPadre(String padre) {
		this.padre = padre;
	}

}
