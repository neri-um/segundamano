package productos.aplicacion.dto;

import productos.dominio.modelo.LugarRecogida;

public class LugarRecogidaDTO {

	private String descripcion;
	private double longitud;
	private double latitud;

	public LugarRecogidaDTO() {
	}

	public static LugarRecogidaDTO fromEntity(LugarRecogida lr) {
		LugarRecogidaDTO dto = new LugarRecogidaDTO();
		dto.descripcion = lr.getDescripcion();
		dto.latitud = lr.getLatitud();
		dto.longitud = lr.getLongitud();
		return dto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

}
