package productos.aplicacion.puertos.salida;

<<<<<<< HEAD
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import productos.dominio.modelo.Producto;
import productos.aplicacion.servicio.ProductoResumen;

import java.math.BigDecimal;
import java.util.List;

@NoRepositoryBean
public interface IRepositorioProductos extends JpaRepository<Producto, String> {


    @Query("SELECT p FROM Producto p " +
    	       "WHERE (:idsCategorias IS NULL OR p.categoria.id IN :idsCategorias) " +
    	       "AND (:descripcion IS NULL OR p.descripcion LIKE CONCAT('%', :descripcion, '%')) " +
    	       "AND (:precioMax IS NULL OR p.precio <= :precioMax) " +
    	       "AND (:estadoNivel IS NULL OR " +
    	       "     CASE p.estado " +
    	       "       WHEN 'NUEVO' THEN 5 WHEN 'COMO_NUEVO' THEN 4 " +
    	       "       WHEN 'BUEN_ESTADO' THEN 3 WHEN 'ACEPTABLE' THEN 2 " +
    	       "       WHEN 'PARA_PIEZAS' THEN 1 END >= :estadoNivel)")
	Page<Producto> buscarProductosConFiltros(List<String> idsCategorias, String descripcion, Integer estadoNivel,
			BigDecimal precio, Pageable pageable);

    @Query("SELECT new productos.aplicacion.servicio.ProductoResumen(" +
    	       "p.id, p.titulo, p.precio, p.fechaPublicacion, p.categoria.nombre, p.visualizaciones) " +
    	       "FROM Producto p " +
    	       "WHERE YEAR(p.fechaPublicacion) = :year AND MONTH(p.fechaPublicacion) = :month " +
    	       "ORDER BY p.visualizaciones DESC")
    	Page<ProductoResumen> obtenerResumenMensual(
    	    @Param("year") int year,
    	    @Param("month") int month, Pageable pageable
    	);


}
=======
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
>>>>>>> aff986665e43008b81af73c9ca6bd4ad369254a7
