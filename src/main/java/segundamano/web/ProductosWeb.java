package segundamano.web;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped; // <--- OJO AL CAMBIO
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import repositorio.RepositorioException;
import segundamano.modelo.Categoria;
import segundamano.modelo.Estado;
import segundamano.servicio.IServicioProductos;
import servicio.FactoriaServicios;

@Named
@SessionScoped 
public class ProductosWeb implements Serializable{
	

	private IServicioProductos servicio;
	private Categoria categoriaInput;
    //private List<ProductoDTO> tareas;
    private String nombreBusqueda; 
    private List<Estado>productos = new LinkedList<>();
    private boolean error;
    
	@Inject
	private FacesContext facesContext;
	
	public ProductosWeb() {
		servicio = FactoriaServicios.getServicio(IServicioProductos.class);
	}
    
    @PostConstruct
    public void init(){
        try {
            //this.productos= servicio.recuperarProductos(categoriaInput);
            error=false;
		} catch (Exception e) {
			error = true;
			
			facesContext.addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
        }
    }


    public void buscar() throws RepositorioException {
    	 //this.productos = servicio.recuperarProductos(categoriaInput);
  
    }

	

}



