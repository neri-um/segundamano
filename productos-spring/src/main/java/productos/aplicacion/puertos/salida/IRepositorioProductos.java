package productos.aplicacion.puertos.salida;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    Page<Producto> buscarProductosConFiltros(
    	    @Param("idsCategorias") List<String> idsCategorias,
    	    @Param("descripcion") String descripcion,
    	    @Param("estadoNivel") Integer estadoNivel,
    	    @Param("precioMax") BigDecimal precioMax,
    	    Pageable pageable);

    @Query("SELECT new productos.aplicacion.servicio.ProductoResumen(" +
    	       "p.id, p.titulo, p.precio, p.fechaPublicacion, p.categoria.nombre, p.visualizaciones) " +
    	       "FROM Producto p " +
    	       "WHERE YEAR(p.fechaPublicacion) = :year AND MONTH(p.fechaPublicacion) = :month " +
    	       "ORDER BY p.visualizaciones DESC")
    	Page<ProductoResumen> obtenerResumenMensual(
    	    @Param("year") int year,
    	    @Param("month") int month,
    	    Pageable pageable
    	);
    
    @Modifying
    @Query("UPDATE UsuarioSimplificado u SET u.nombre = :nombre, " +
           "u.apellidos = :apellidos, u.email = :email WHERE u.id = :id")
    void actualizarUsuario(@Param("id") String id,
                           @Param("nombre") String nombre,
                           @Param("apellidos") String apellidos,
                           @Param("email") String email);

}

