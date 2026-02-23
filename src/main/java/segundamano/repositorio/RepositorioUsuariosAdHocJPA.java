package segundamano.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import segundamano.modelo.Usuario;
import utils.EntityManagerHelper;

public class RepositorioUsuariosAdHocJPA extends RepositorioUsuariosJPA
        implements RepositorioUsuariosAdHoc {

    @Override
    public Usuario getByEmail(String email) throws EntidadNoEncontrada, RepositorioException {
        try {
            EntityManager em = EntityManagerHelper.getEntityManager();

            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
            query.setParameter("email", email);
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);

            return query.getSingleResult();

        } catch (NoResultException e) {
            throw new EntidadNoEncontrada("Usuario con email " + email + " no encontrado.");
        } catch (RuntimeException e) {
            throw new RepositorioException("Error al buscar usuario por email.", e);
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }
}
