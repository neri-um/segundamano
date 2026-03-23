package productos.aplicacion.output;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import productos.aplicacion.servicio.ProductoResumen;
import productos.dominio.modelo.Estado;
import productos.dominio.modelo.Producto;
import productos.infraestructura.persistencia.base.EntidadNoEncontrada;
import productos.infraestructura.persistencia.base.RepositorioException;

public interface IRepositorioProductos {
	Optional<Producto> findById(String id);

	Producto save(Producto producto);

	void incrementarVisualizaciones(String id) throws RepositorioException, EntidadNoEncontrada;

	List<ProductoResumen> obtenerResumenMensual(YearMonth mes) throws RepositorioException;

	List<Producto> buscarProductosConFiltros(String descripcion, List<String> idsCategorias, Estado estado,
			BigDecimal precioMax) throws RepositorioException;
}
