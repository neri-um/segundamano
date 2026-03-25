package productos.aplicacion.puertos.salida;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import productos.aplicacion.servicio.ProductoResumen;
import productos.dominio.modelo.Estado;
import productos.dominio.modelo.Producto;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IRepositorioProductos {

	List<ProductoResumen> obtenerResumenMensual(YearMonth mes) throws RepositorioException;

	List<Producto> buscarProductosConFiltros(String descripcion, List<String> idsCategorias, Estado estado,
			BigDecimal precioMax) throws RepositorioException;
}
