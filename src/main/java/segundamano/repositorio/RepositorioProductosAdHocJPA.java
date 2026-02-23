package segundamano.repositorio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import repositorio.RepositorioException;
import segundamano.modelo.Estado;
import segundamano.modelo.Producto;
import segundamano.servicio.ProductoResumen;
import utils.EntityManagerHelper;

public class RepositorioProductosAdHocJPA extends RepositorioProductosJPA
        implements RepositorioProductosAdHoc {

	@Override
	public List<ProductoResumen> obtenerResumenMensual(YearMonth mes) throws RepositorioException {
	    EntityManager em = EntityManagerHelper.getEntityManager();

	    LocalDateTime inicioMes = mes.atDay(1).atStartOfDay();
	    LocalDateTime finMes = mes.atEndOfMonth().atTime(23, 59, 59, 999999999);

	    try {
	        String jpql = "SELECT new segundamano.servicio.ProductoResumen("
	                    + "p.id, p.titulo, p.precio, p.fechaPublicacion, p.categoria.nombre, p.visualizaciones) "
	                    + "FROM Producto p "
	                    + "WHERE p.fechaPublicacion >= :inicio "
	                    + "AND p.fechaPublicacion <= :fin "
	                    + "ORDER BY p.visualizaciones DESC";

	        TypedQuery<ProductoResumen> q = em.createQuery(jpql, ProductoResumen.class);
	        q.setParameter("inicio", inicioMes);
	        q.setParameter("fin", finMes);
	        q.setHint(QueryHints.REFRESH, HintValues.TRUE);

	        return q.getResultList();

	    } catch (RuntimeException e) {
	        throw new RepositorioException("Error al obtener resumen mensual", e);
	    } finally {
	        EntityManagerHelper.closeEntityManager();
	    }
	}


    @Override
    public List<Producto> buscarProductosConFiltros(String texto, List<String> idsCategorias,
            Estado estadoMinimo, BigDecimal precioMaximo) throws RepositorioException {

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");
            Map<String, Object> params = new HashMap<>();

            if (texto != null && !texto.trim().isEmpty()) {
                jpql.append(" AND (LOWER(p.titulo) LIKE LOWER(:t) OR LOWER(p.descripcion) LIKE LOWER(:t))");
                params.put("t", "%" + texto + "%");
            }

            if (idsCategorias != null && !idsCategorias.isEmpty()) {
                jpql.append(" AND p.categoria.id IN :cats");
                params.put("cats", idsCategorias);
            }

            if (estadoMinimo != null) {
                // En tu proyecto ya lo estabas resolviendo así: estados "igual o mejor"
                jpql.append(" AND p.estado IN :estados");
                params.put("estados", estadoMinimo.getEstadosIgualOMejor());
            }

            if (precioMaximo != null) {
                jpql.append(" AND p.precio <= :pmax");
                params.put("pmax", precioMaximo);
            }

            TypedQuery<Producto> q = em.createQuery(jpql.toString(), Producto.class);
            q.setHint(QueryHints.REFRESH, HintValues.TRUE);
            for (Map.Entry<String, Object> e : params.entrySet()) q.setParameter(e.getKey(), e.getValue());

            return q.getResultList();

        } catch (RuntimeException e) {
            throw new RepositorioException("Error buscando productos con filtros", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    @Override
    public void incrementarVisualizaciones(String idProducto) throws RepositorioException {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();

            Producto p = em.find(Producto.class, idProducto);
            if (p != null) {
                p.setVisualizaciones(p.getVisualizaciones() + 1);
                em.merge(p);
            }

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RepositorioException("Error incrementando visualizaciones de " + idProducto, e);
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            EntityManagerHelper.closeEntityManager();
        }
    }

    @Override
    public List<Producto> buscarProductosPorVendedor(String idVendedor) throws RepositorioException {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            String jpql = "SELECT p FROM Producto p "
                        + "JOIN FETCH p.vendedor "
                        + "LEFT JOIN FETCH p.categoria "
                        + "WHERE p.vendedor.id = :idV "
                        + "ORDER BY p.fechaPublicacion DESC";

            TypedQuery<Producto> q = em.createQuery(jpql, Producto.class);
            q.setParameter("idV", idVendedor);
            q.setHint(QueryHints.REFRESH, HintValues.TRUE);

            return q.getResultList();

        } catch (RuntimeException e) {
            throw new RepositorioException("Error buscando productos del vendedor " + idVendedor, e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }


}
