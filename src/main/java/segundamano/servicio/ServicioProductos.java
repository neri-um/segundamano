package segundamano.servicio;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import segundamano.modelo.Categoria;
import segundamano.modelo.Estado;
import segundamano.modelo.LugarRecogida;
import segundamano.modelo.Producto;
import segundamano.modelo.Usuario;
import segundamano.repositorio.RepositorioProductosAdHoc;
import segundamano.repositorio.RepositorioProductosAdHocJPA;
import utils.EntityManagerHelper;

public class ServicioProductos implements IServicioProductos{

	private RepositorioProductosAdHoc repositorio;

    public ServicioProductos() throws IOException {
        this.repositorio = new RepositorioProductosAdHocJPA();
    }
    
	@Override
	public String altaProducto(String titulo, String descripcion, double precio, Estado estado, Categoria categoria,
			boolean envio, Usuario vendedor) throws RepositorioException {
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
			producto.setFechaPublicacion(LocalDateTime.now());
		return repositorio.add(producto);
	}

	@Override
	public void asignarRecogida(String id, double longitud, double latitud, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		if (id==null || id.isEmpty()) {
			throw new IllegalArgumentException("ERROR: ID no puede ser null o vacío");
		}
		if(longitud < 0 ) {
			throw new IllegalArgumentException("ERROR: longitud no puede ser negativo");
		}
		if(latitud < 0 ) {
			throw new IllegalArgumentException("ERROR: latitud no puede ser negativo");
		}
		if (descripcion==null || descripcion.isEmpty()) {
			throw new IllegalArgumentException("ERROR: descripcion no puede ser null o vacío");
		}
		Producto p =repositorio.getById(id);
		LugarRecogida lr = new LugarRecogida(descripcion, longitud, latitud);
		p.setLugar(lr);
		repositorio.update(p);
		
	}

	@Override
	public void modificarProducto(String id, double precio, String descripcion) throws RepositorioException, EntidadNoEncontrada {
		if (id==null || id.isEmpty()) {
			throw new IllegalArgumentException("ERROR: ID no puede ser null o vacío");
		}
		Producto p = repositorio.getById(id);
		if(precio >= 0.0) {
			p.setPrecio(precio);
		}
		if (descripcion != null && !descripcion.trim().isEmpty()) {
			p.setDescripcion(descripcion);
		}
		repositorio.update(p);
	}

	@Override
	public void añadirVisualizaciones(String id) throws RepositorioException, EntidadNoEncontrada {
		if (id==null || id.isEmpty()) {
			throw new IllegalArgumentException("ERROR: ID no puede ser null o vacío");
		}
		Producto p = repositorio.getById(id);
		int visualizaciones = p.getVisualizaciones();
		p.setVisualizaciones(visualizaciones++);
		repositorio.update(p);
		
	}

	@Override
	public List<ProductoResumen> historialDelMes(int mes, int anio) throws RepositorioException {
	    EntityManager em = EntityManagerHelper.getEntityManager();
	    try {
	        String jpql = "SELECT new segundamano.servicio.ProductoResumen(p.id, p.titulo, p.precio, p.fechaPublicacion, p.categoria.nombre, p.visualizaciones) " +
	                      "FROM Producto p " +
	                      "WHERE FUNCTION('MONTH', p.fechaPublicacion) = :mes " +
	                      "AND FUNCTION('YEAR', p.fechaPublicacion) = :anio " +
	                      "ORDER BY p.visualizaciones DESC";

	        TypedQuery<ProductoResumen> query = em.createQuery(jpql, ProductoResumen.class);
	        query.setParameter("mes", mes);
	        query.setParameter("anio", anio);
	        
	        return query.getResultList();
	    } catch (Exception e) {
	        throw new RepositorioException("Error al recuperar el historial del mes", e);
	    } finally {
	        EntityManagerHelper.closeEntityManager();
	    }
	}


	@Override
	public List<Producto> buscarProductos(String categoriaId, String descripcion, Estado estado, Double precioMax) throws RepositorioException {
	    EntityManager em = EntityManagerHelper.getEntityManager();
	    try {
	        StringBuilder sb = new StringBuilder("SELECT p FROM Producto p WHERE 1=1 ");
	        if (categoriaId != null && !categoriaId.isEmpty()) {
	            sb.append("AND (p.categoria.id = :catId OR p.categoria.categoriaPadre.id = :catId) ");
	        }
	        if (descripcion != null && !descripcion.isEmpty()) {
	            sb.append("AND LOWER(p.descripcion) LIKE LOWER(:desc) ");
	        }
	        if (precioMax != null && precioMax > 0) {
	            sb.append("AND p.precio <= :precio ");
	        }
	        if (estado != null) {
	            sb.append("AND p.estado <= :estado "); 
	        }

	        TypedQuery<Producto> query = em.createQuery(sb.toString(), Producto.class);

	        if (categoriaId != null && !categoriaId.isEmpty()) {
	            query.setParameter("catId", categoriaId);
	        }
	        if (descripcion != null && !descripcion.isEmpty()) {
	            query.setParameter("desc", "%" + descripcion + "%"); 
	        }
	        if (precioMax != null && precioMax > 0) {
	            query.setParameter("precio", precioMax);
	        }
	        if (estado != null) {
	            query.setParameter("estado", estado);
	        }
	        return query.getResultList();

	    } catch (Exception e) {
	        throw new RepositorioException("Error en el buscador de productos", e);
	    } finally {
	        EntityManagerHelper.closeEntityManager();
	    }
	}


}
