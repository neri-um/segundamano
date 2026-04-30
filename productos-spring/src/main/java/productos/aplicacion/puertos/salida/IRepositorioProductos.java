package productos.aplicacion.puertos.salida;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import productos.dominio.modelo.Producto;
import productos.aplicacion.servicio.ProductoResumen;

import java.math.BigDecimal;
import java.util.List;

@NoRepositoryBean
public interface IRepositorioProductos extends CrudRepository<Producto, String> {

    Page<Producto> buscarProductosConFiltros(
        List<String> idsCategorias,
        String descripcion,
        Integer estadoNivel,
        BigDecimal precioMax,
        Pageable pageable);

    Page<ProductoResumen> obtenerResumenMensual(
        int year,
        int month,
        Pageable pageable);
}