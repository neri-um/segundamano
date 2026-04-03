package productos.aplicacion.dto;

public class ModificarProductoDTO {

	private String descripcion;
	private Double precio; // con mayúscula para que pueda ser null

	public ModificarProductoDTO() {
		// para leer el JSON
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}