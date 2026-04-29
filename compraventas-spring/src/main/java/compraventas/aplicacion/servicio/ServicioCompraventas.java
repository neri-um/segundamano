package compraventas.aplicacion.servicio;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import compraventas.aplicacion.puertos.entrada.IServicioCompraventas;
import compraventas.aplicacion.puertos.entrada.ManejadorEventos;
import compraventas.aplicacion.puertos.salida.DatosProducto;
import compraventas.aplicacion.puertos.salida.IPublicadorEventos;
import compraventas.aplicacion.puertos.salida.IPuertoProductos;
import compraventas.aplicacion.puertos.salida.IPuertoUsuarios;
import compraventas.aplicacion.puertos.salida.IRepositorioCompraventas;
import compraventas.dominio.modelo.Compraventa;
import compraventas.eventos.EventoCompraventaCreada;
import repositorio.EntidadNoEncontrada;

@Service
public class ServicioCompraventas implements IServicioCompraventas, ManejadorEventos {

	private final IRepositorioCompraventas repositorio;
	private final IPuertoProductos puertoProductos;
	private final IPuertoUsuarios puertoUsuarios;

	@Autowired
	private IPublicadorEventos publicadorEventos;

	public ServicioCompraventas(IRepositorioCompraventas repositorio, IPuertoProductos puertoProductos,
			IPuertoUsuarios puertoUsuarios, IPublicadorEventos publicadorEventos) {
		this.repositorio = repositorio;
		this.puertoProductos = puertoProductos;
		this.puertoUsuarios = puertoUsuarios;
		this.publicadorEventos = publicadorEventos;
	}

	@Override
	public Compraventa realizarCompraventa(String idProducto, String idComprador) throws IOException {
	    DatosProducto producto = puertoProductos.getProducto(idProducto);

	    if (producto.isVendido()) {
	        throw new IllegalStateException("El producto ya ha sido vendido");
	    }

	    String nombreVendedor = puertoUsuarios.getNombreUsuario(producto.getIdVendedor());
	    String nombreComprador = puertoUsuarios.getNombreUsuario(idComprador);

	    Compraventa c = new Compraventa(
	        idProducto,
	        producto.getTitulo(),
	        producto.getPrecio(),
	        producto.getRecogida(),
	        producto.getIdVendedor(),
	        nombreVendedor,
	        idComprador,
	        nombreComprador
	    );

	    String id = repositorio.guardar(c).getId();
	    publicadorEventos.publicarEvento(new EventoCompraventaCreada(id, producto.getIdVendedor(), idComprador, idProducto));
	    return c;
	}

	@Override
	public Page<Compraventa> obtenerComprasDeUsuario(String idComprador, Pageable pageable) {
		return repositorio.buscarPorComprador(idComprador, pageable);
	}

	@Override
	public Page<Compraventa> obtenerVentasDeUsuario(String idVendedor, Pageable pageable) {
		return repositorio.buscarPorVendedor(idVendedor, pageable);
	}

	@Override
	public Page<Compraventa> obtenerCompraventasEntreUsuarios(String idComprador, String idVendedor,
			Pageable pageable) {
		return repositorio.buscarPorCompradorYVendedor(idComprador, idVendedor, pageable);
	}

	@Override
	public Compraventa obtenerCompraventa(String id) throws EntidadNoEncontrada {
		return repositorio.buscarPorId(id);
	}
	
	
	@Override
	public void actualizarNombreUsuario(String idUsuario, String nombre, String apellidos) {
	    String nuevoNombre = nombre + " " + apellidos;

	    repositorio.buscarPorIdVendedor(idUsuario).forEach(c -> {
	        c.setNombreVendedor(nuevoNombre);
	        repositorio.guardar(c);
	    });

	    repositorio.buscarPorIdComprador(idUsuario).forEach(c -> {
	        c.setNombreComprador(nuevoNombre);
	        repositorio.guardar(c);
	    });
	}

	@Override
    public void usuarioModificado(String idUsuario, String nombre, String apellidos) {
        actualizarNombreUsuario(idUsuario, nombre, apellidos);
    }



}