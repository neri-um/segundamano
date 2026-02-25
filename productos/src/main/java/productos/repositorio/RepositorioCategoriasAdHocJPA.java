package productos.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import productos.modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import utils.EntityManagerHelper;

public class RepositorioCategoriasAdHocJPA extends RepositorioCategoriasJPA
        implements RepositorioCategoriasAdHoc {

    @Override
    public void actualizarDescripcion(String idCategoria, String descripcion)
            throws RepositorioException, EntidadNoEncontrada {

        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();

            Categoria c = em.find(Categoria.class, idCategoria);
            if (c == null) throw new EntidadNoEncontrada("Categoría " + idCategoria + " no existe");

            c.setDescripcion(descripcion);
            em.merge(c);

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RepositorioException("Error actualizando descripción de categoría " + idCategoria, e);
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            EntityManagerHelper.closeEntityManager();
        }
    }

    @Override
    public List<Categoria> recuperarCategoriasRaiz() throws RepositorioException {
        try {
            EntityManager em = EntityManagerHelper.getEntityManager();

            String jpql = "SELECT c FROM Categoria c WHERE c.padre IS NULL";

            TypedQuery<Categoria> q = em.createQuery(jpql, Categoria.class);
            q.setHint(QueryHints.REFRESH, HintValues.TRUE);
            return q.getResultList();

        } catch (RuntimeException e) {
            throw new RepositorioException("Error recuperando categorías raíz", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }


    @Override
    public List<Categoria> recuperarDescendientes(String idCategoria)
            throws RepositorioException, EntidadNoEncontrada {
        Categoria c = getById(idCategoria); // método de RepositorioJPA, lanza EntidadNoEncontrada
        List<Categoria> res = new ArrayList<>();
        recogerDescendientes(c, res);
        return res;
    }

    private void recogerDescendientes(Categoria c, List<Categoria> res) {
        if (c.getSubcategorias() == null) return;
        for (Categoria sub : c.getSubcategorias()) {
            res.add(sub);
            recogerDescendientes(sub, res);
        }
    }

    @Override
    public List<String> getIdsCategoriaYDescendientes(String idCategoria)
            throws RepositorioException, EntidadNoEncontrada {
        Categoria c = getById(idCategoria);
        List<String> ids = new ArrayList<>();
        recogerIds(c, ids);
        return ids;
    }

    private void recogerIds(Categoria c, List<String> ids) {
        ids.add(c.getId());
        if (c.getSubcategorias() == null) return;
        for (Categoria sub : c.getSubcategorias()) recogerIds(sub, ids);
    }
}
