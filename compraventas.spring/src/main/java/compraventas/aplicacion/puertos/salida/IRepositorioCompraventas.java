package compraventas.aplicacion.puertos.salida;

import compraventas.dominio.modelo.Compraventa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRepositorioCompraventas {

	Page<Compraventa> buscarPorComprador(String idComprador, Pageable pageable);
	Page<Compraventa> buscarPorVendedor(String idVendedor, Pageable pageable);
	Page<Compraventa> buscarPorCompradorYVendedor(String idComprador, String idVendedor, Pageable pageable);
}