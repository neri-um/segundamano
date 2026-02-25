package productos.repositorio;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import productos.modelo.Estado;
import productos.modelo.Producto;
import productos.servicio.ProductoResumen;
import repositorio.RepositorioException;

public interface RepositorioProductosAdHoc {

	List<ProductoResumen> obtenerResumenMensual(YearMonth mes) throws RepositorioException;


    List<Producto> buscarProductosConFiltros(String texto, List<String> idsCategorias,
            Estado estadoMinimo, BigDecimal precioMaximo) throws RepositorioException;

    void incrementarVisualizaciones(String idProducto) throws RepositorioException;

    List<Producto> buscarProductosPorVendedor(String idVendedor) throws RepositorioException;
}
