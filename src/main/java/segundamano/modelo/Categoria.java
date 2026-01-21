package segundamano.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import repositorio.Identificable;

@XmlRootElement(name="categoria") 
@XmlAccessorType(XmlAccessType.FIELD)
@Entity //JPA
@Table(name = "categorias")
public class Categoria implements Identificable{
	@Id
	@XmlAttribute(name ="id")
	private String id;
	
	@XmlAttribute(name="ruta")
	@Column(nullable = false)
	private String ruta;
	
	@XmlElement(name="nombre")
	@Column(nullable = false, unique = true)
	private String nombre;
	
	@XmlTransient
	@Lob
	private String descripcion; //no aparece en el xml
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padre_id") // columna FK a la categoría padre
    private Categoria padre;
	
	@XmlElement(name="categoria")
	@OneToMany(mappedBy = "padre", cascade = CascadeType.ALL, orphanRemoval = true) // sin el padre no tiene sentido que exista
    private List<Categoria> subcategorias = new ArrayList<>();
	
	public Categoria() {
		//JAXB
	}
	public Categoria(String nombre, String descripcion, String ruta, List<Categoria> subcategorias) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ruta = ruta;
		this.subcategorias = subcategorias;
		this.id = UUID.randomUUID().toString();
	}

	// --> GETTERS
	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getRuta() {
		return ruta;
	}

	public Collection<? extends Categoria> getSubcategorias() {
		return subcategorias;
	}
	
	// --> SETTERS
	public void setId(String id) {
		this.id = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	public void addSubcategoria(Categoria subcategoria) {
	    this.subcategorias.add(subcategoria); 
	    subcategoria.setPadre(this);         
	}
	
	public void setSubcategorias(List<Categoria> subcategorias) {
		this.subcategorias = subcategorias;
	}
	
	public void setPadre(Categoria padre) {
		this.padre = padre;
		
	}


	
}
