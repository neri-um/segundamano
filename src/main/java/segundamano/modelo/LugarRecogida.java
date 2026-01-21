package segundamano.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table; 

@Entity
@Table(name = "lugares_recogida")
public class LugarRecogida implements Serializable { 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String descripcion;
	private double longitud;
	private double latitud;

	public LugarRecogida() {
		//JAXB 
	}
	public LugarRecogida(String descripcion, double longitud, double latitud) {
		this.descripcion = descripcion;
		this.longitud = longitud;
		this.latitud = latitud;
	}

	// --> GETTERS
	public String getDescripcion() {
		return descripcion;
	}

	public double getLongitud() {
		return longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	// --> SETTERS
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}


}
