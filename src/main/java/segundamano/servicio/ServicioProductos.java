package segundamano.servicio;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import segundamano.modelo.Categoria;
import segundamano.modelo.Estado;
import segundamano.modelo.Producto;
import segundamano.modelo.Usuario;
import segundamano.repositorio.RepositorioProductosAdHocJPA;

public class ServicioProductos implements IServicioProductos{

	private RepositorioProductosAdHocJPA repositorio;

    public ServicioProductos() throws IOException {
        this.repositorio = new RepositorioProductosAdHocJPA();
    }
	@Override
	public String altaProducto(String titulo, String descripcion, double precio, Estado estado, Categoria categoria,
			boolean envio, Usuario vendedor) {
			if (titulo==null || titulo.isEmpty()) {
				throw new IllegalArgumentException("ERROR: titulo no puede ser null o vacío");
			}
			if (descripcion==null || descripcion.isEmpty()) {
				throw new IllegalArgumentException("ERROR: descripcion no puede ser null o vacío");
			}
			if(precio < 0 ) {
				throw new IllegalArgumentException("ERROR: precio no puede ser negativo");
			}
			if (estado==null) {
				throw new IllegalArgumentException("ERROR: estado no puede ser null");
			}
			if (categoria==null) {
				throw new IllegalArgumentException("ERROR: categoria no puede ser null");
			}
			if (vendedor==null) {
				throw new IllegalArgumentException("ERROR: idVendedor no puede ser null");
			}
			
			Producto producto = new Producto(titulo, descripcion, precio, estado, categoria, envio, vendedor);
		return null;
	}

	@Override
	public void asignarRecogida(String id, double longitud, double latitud, String descripcion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarProducto(String id, double precio, String descripcion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void añadirVisualizaciones(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Producto> getByMesAnyo(LocalDate fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> buscarProductos(Categoria categoria, String descripcion, Estado estado, double precio) {
		// TODO Auto-generated method stub
		return null;
	}

}
