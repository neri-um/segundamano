package productos.aplicacion.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productos.aplicacion.input.IServicioProductos;
import productos.aplicacion.output.IRepositorioCategorias;
import productos.aplicacion.output.IRepositorioProductos;
import productos.dominio.modelo.Categoria;
import productos.dominio.modelo.Estado;
import productos.dominio.modelo.LugarRecogida;
import productos.dominio.modelo.Producto;
import productos.dominio.modelo.UsuarioSimplificado;
import productos.infraestructura.persistencia.base.EntidadNoEncontrada;
import productos.infraestructura.persistencia.base.RepositorioException;

@Service
@Transactional
public class ServicioProductos implements IServicioProductos {

	private IRepositorioProductos repositorio;
	private IRepositorioCategorias repositorioCategorias; 

	@Autowired
	public ServicioProductos(IRepositorioProductos repositorio,
	                          IRepositorioCategorias repositorioCategorias) { 
	    this.repositorio = repositorio;
	    this.repositorioCategorias = repositorioCategorias; 
	}


	public Producto getProducto(String id) throws RepositorioException, EntidadNoEncontrada {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		Optional<Producto> resultado = repositorio.findById(id);
		if (resultado.isPresent() == false)
			throw new EntidadNoEncontrada("No existe encuesta con id: " + id);
		else
			return resultado.get();
	}

	@Override
	public String altaProducto(String titulo, String descripcion, double precio, Estado estado, Categoria categoria,
			boolean envio, UsuarioSimplificado vendedor) throws RepositorioException {

		if (titulo == null || titulo.isEmpty())
			throw new IllegalArgumentException("ERROR: titulo no puede ser null o vacío");
		if (descripcion == null || descripcion.isEmpty())
			throw new IllegalArgumentException("ERROR: descripcion no puede ser null o vacío");
		if (precio < 0)
			throw new IllegalArgumentException("ERROR: precio no puede ser negativo");
		if (estado == null)
			throw new IllegalArgumentException("ERROR: estado no puede ser null");
		if (categoria == null)
			throw new IllegalArgumentException("ERROR: categoria no puede ser null");
		if (vendedor == null)
			throw new IllegalArgumentException("ERROR: vendedor no puede ser null");

		Producto producto = new Producto(titulo, descripcion, precio, estado, categoria, envio, vendedor);
		producto.setFechaPublicacion(LocalDateTime.now());
		
		return repositorio.save(producto).getId();
	}

	@Override
	public void asignarRecogida(String id, double longitud, double latitud, String descripcion)
			throws RepositorioException, EntidadNoEncontrada {

		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("ERROR: ID no puede ser null o vacío");
		if (descripcion == null || descripcion.isEmpty())
			throw new IllegalArgumentException("ERROR: descripcion no puede ser null o vacío");

		Producto p = getProducto(id);
		LugarRecogida lr = new LugarRecogida(descripcion, longitud, latitud);
		p.setLugar(lr);
		repositorio.save(p);
	}

	@Override
	public void modificarProducto(String id, double precio, String descripcion)
			throws RepositorioException, EntidadNoEncontrada {

		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("ERROR: ID no puede ser null o vacío");

		Producto p = getProducto(id);
		if (precio >= 0.0)
			p.setPrecio(precio);
		if (descripcion != null && !descripcion.trim().isEmpty())
			p.setDescripcion(descripcion);
		repositorio.save(p);
	}

	@Override
	public void añadirVisualizaciones(String id) throws RepositorioException, EntidadNoEncontrada {

		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("ERROR: ID no puede ser null o vacío");

		repositorio.incrementarVisualizaciones(id);
	}

	@Override
	public List<ProductoResumen> historialDelMes(int mes, int anio) throws RepositorioException {
		YearMonth yearMonth = YearMonth.of(anio, mes);
		return repositorio.obtenerResumenMensual(yearMonth);
	}

	@Override
	public List<Producto> buscarProductos(String categoriaId, String descripcion, Estado estado, Double precioMax)
			throws RepositorioException, EntidadNoEncontrada {

		List<String> idsCategorias = null;

		// Si hay categoría, obtener ella + todos sus descendientes
		if (categoriaId != null && !categoriaId.isEmpty()) {
			idsCategorias = repositorioCategorias.getIdsCategoriaYDescendientes(categoriaId);
		}

		BigDecimal precio = (precioMax != null) ? BigDecimal.valueOf(precioMax) : null;

		return repositorio.buscarProductosConFiltros(descripcion, idsCategorias, estado, precio);
	}
}
