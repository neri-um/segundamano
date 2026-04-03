package compraventas.aplicacion.servicio;

import compraventas.aplicacion.puertos.entrada.IServicioCompraventas;
import compraventas.aplicacion.puertos.salida.IRepositorioCompraventas;
import compraventas.aplicacion.puertos.salida.IPuertoProductos;
import compraventas.aplicacion.puertos.salida.IPuertoUsuarios;
import compraventas.dominio.modelo.Compraventa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioCompraventas implements IServicioCompraventas {

    private final IRepositorioCompraventas repositorio;
    private final IPuertoProductos puertoProductos;
    private final IPuertoUsuarios puertoUsuarios;

    public ServicioCompraventas(IRepositorioCompraventas repositorio,
                                 IPuertoProductos puertoProductos,
                                 IPuertoUsuarios puertoUsuarios) {
        this.repositorio = repositorio;
        this.puertoProductos = puertoProductos;
        this.puertoUsuarios = puertoUsuarios;
    }

    @Override
    public Compraventa realizarCompraventa(String idProducto, String idComprador) {
        // Obtener datos del producto
        String idVendedor   = puertoProductos.getIdVendedor(idProducto);
        String titulo       = puertoProductos.getTitulo(idProducto);
        double precio       = puertoProductos.getPrecio(idProducto);
        String recogida     = puertoProductos.getRecogida(idProducto);

        // Obtener nombres de usuarios
        String nombreVendedor  = puertoUsuarios.getNombreUsuario(idVendedor);
        String nombreComprador = puertoUsuarios.getNombreUsuario(idComprador);

        Compraventa c = new Compraventa(
                idProducto, titulo, precio, recogida,
                idVendedor, nombreVendedor,
                idComprador, nombreComprador);

        return repositorio.guardar(c);
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
    public Page<Compraventa> obtenerCompraventasEntreUsuarios(String idComprador, String idVendedor, Pageable pageable) {
        return repositorio.buscarPorCompradorYVendedor(idComprador, idVendedor, pageable);
    }
}