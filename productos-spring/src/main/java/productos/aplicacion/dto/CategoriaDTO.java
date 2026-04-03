package productos.aplicacion.dto;

import javax.validation.constraints.NotBlank;

import productos.dominio.modelo.Categoria;

public class CategoriaDTO {

	@NotBlank
    private String nombre;
	@NotBlank
    private String descripcion;
	@NotBlank
    private String ruta;

    public CategoriaDTO() {
        // para el fromEntity linea 1
    }

    public static CategoriaDTO fromEntity(Categoria c) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.nombre = c.getNombre();
        dto.descripcion = c.getDescripcion();
        dto.ruta = c.getRuta();
        return dto;
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


}