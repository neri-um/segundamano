package productos.aplicacion.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import productos.aplicacion.puertos.entrada.IServicioProductos;
import productos.aplicacion.puertos.entrada.ManejadorEventos;
import productos.aplicacion.puertos.salida.IRepositorioCategorias;
import productos.aplicacion.puertos.salida.IRepositorioProductos;
import productos.aplicacion.puertos.salida.IRepositorioUsuarios;
import productos.aplicacion.puertos.salida.ProductosPuerto;
import productos.dominio.modelo.Categoria;
import productos.dominio.modelo.Estado;
import productos.dominio.modelo.LugarRecogida;
import productos.dominio.modelo.Producto;
import productos.dominio.modelo.UsuarioSimplificado;
import productos.eventos.EventoProductoCreado;
import productos.eventos.EventoProductoModificado;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@Service
@Transactional
public class ServicioProductos implements IServicioProductos, ManejadorEventos {

	private IRepositorioProductos repositorio;
	private IRepositorioCategorias repositorioCategorias; 
	private ProductosPuerto publicador;
	private IRepositorioUsuarios repositorioUsuarios;

	@Autowired
	public ServicioProductos(IRepositorioProductos repositorio,
	                          IRepositorioCategorias repositorioCategorias,
	                          ProductosPuerto publicador,
	                          IRepositorioUsuarios repositorioUsuarios) {
	    this.repositorio = repositorio;
	    this.repositorioCategorias = repositorioCategorias;
	    this.publicador = publicador;
	    this.repositorioUsuarios = repositorioUsuarios;
	}


	public Producto getProducto(String id) throws RepositorioException, EntidadNoEncontrada {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		Optional<Producto> resultado = repositorio.findById(id);
		if (resultado.isPresent() == false)
			throw new EntidadNoEncontrada("No existe producto con id: " + id);
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
		
		String id = repositorio.save(producto).getId();
		publicador.publicarEvento(new EventoProductoCreado(
		    id,
		    vendedor.getId(),
		    titulo,
		    precio
		));
		return id;
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
		publicador.publicarEvento(new EventoProductoModificado(
		    p.getId(),
		    p.getTitulo(),
		    p.getPrecio()
		));
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

		Producto p = repositorio.findById(id).orElseThrow(() -> new EntidadNoEncontrada(id));
		p.setVisualizaciones(p.getVisualizaciones() + 1);
		repositorio.save(p);
	}


	@Override
	@Transactional(readOnly = true)
	public Page<ProductoResumen> historialDelMes(int mes, int anyo, Pageable pageable) throws RepositorioException {
	    return repositorio.obtenerResumenMensual(anyo, mes, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> buscarProductos(String categoria, String descripcion, 
	        Estado estado, Double precioMax, Pageable pageable) throws RepositorioException, EntidadNoEncontrada {

	    List<String> idsCategorias = null;
	    if (categoria != null && !categoria.isEmpty()) {
	        idsCategorias = repositorioCategorias.getIdsCategoriaYDescendientes(categoria);
	    }

	    Integer estadoNivel = (estado != null) ? estado.getNivel() : null;
	    BigDecimal precio = (precioMax != null) ? BigDecimal.valueOf(precioMax) : null;

	    return repositorio.buscarProductosConFiltros(idsCategorias, descripcion, estadoNivel, precio, pageable);
	}
	
	@Override
	public void marcarComoVendido(String idProducto) throws RepositorioException, EntidadNoEncontrada {
	    Producto p = getProducto(idProducto);
	    p.setVendido(true);
	    repositorio.save(p);
	}
	
	@Override
	@Transactional
	public void actualizarUsuarioSimplificado(String id, String nombre,
	                                           String apellidos, String email) {
	    Optional<UsuarioSimplificado> resultado = repositorioUsuarios.findById(id);
	    if (resultado.isPresent()) {
	        UsuarioSimplificado usuario = resultado.get();
	        usuario.setNombre(nombre);
	        usuario.setApellidos(apellidos);
	        usuario.setEmail(email);
	        repositorioUsuarios.save(usuario);
	    }
	}


	@Override
	public void compraventaCreada(String idCompraventa, String idProducto) {
        try {
            marcarComoVendido(idProducto);
        } catch (Exception e) {
            System.err.println("[productos] Error: " + e.getMessage());
        }
	}


	@Override
	public void usuarioModificado(String idUsuario, String nombre, String apellidos, String email) {
        actualizarUsuarioSimplificado(idUsuario, nombre, apellidos, email);
		
	}
	
	
}
