package segundamano.web;

import java.io.Serializable;

public class ProductosDetailWeb implements Serializable{
/**
 * 	private IServicioProductos servicio;
    private List<ProductoDTO> tareas;

    
    @Inject
	protected FacesContext facesContext;
		
	public TareasDetailWeb() {		
		servicio = FactoriaServicios.getServicio(IServicioTareas.class);
    }
	@PostConstruct
    public void load() {
        try {
        	this.tareas = servicio.recuperarProductos(categoriaInput);
        } catch (Exception e) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    }
 */
}