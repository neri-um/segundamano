package compraventas.aplicacion.puertos.salida;

import compraventas.dominio.modelo.Compraventa;
import repositorio.EntidadNoEncontrada;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRepositorioCompraventas {

    Compraventa guardar(Compraventa compraventa);

    Page<Compraventa> buscarPorComprador(String idComprador, Pageable pageable);

    Page<Compraventa> buscarPorVendedor(String idVendedor, Pageable pageable);

    Page<Compraventa> buscarPorCompradorYVendedor(String idComprador, String idVendedor, Pageable pageable);
    
    Compraventa buscarPorId(String id) throws EntidadNoEncontrada;
    
    List<Compraventa> buscarPorIdVendedor(String idVendedor);
    
    List<Compraventa> buscarPorIdComprador(String idComprador);
}