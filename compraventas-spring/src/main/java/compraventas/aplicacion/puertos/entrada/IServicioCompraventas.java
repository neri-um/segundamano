package compraventas.aplicacion.puertos.entrada;

import compraventas.dominio.modelo.Compraventa;
import repositorio.EntidadNoEncontrada;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServicioCompraventas {

    Compraventa realizarCompraventa(String idProducto, String idComprador);

    Page<Compraventa> obtenerComprasDeUsuario(String idComprador, Pageable pageable);

    Page<Compraventa> obtenerVentasDeUsuario(String idVendedor, Pageable pageable);

    Page<Compraventa> obtenerCompraventasEntreUsuarios(String idComprador, String idVendedor, Pageable pageable);
    
    Compraventa obtenerCompraventa(String id) throws EntidadNoEncontrada;
}