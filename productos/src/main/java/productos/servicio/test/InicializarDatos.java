package productos.servicio.test;

import utils.EntityManagerHelper;
import productos.modelo.UsuarioSimplificado;
import javax.persistence.EntityManager;

/**
 * Programa provisional para cargar usuarios simplificados iniciales
 * en la BD de productos.
 * 
 * En el futuro, esto se sustituirá por la recepción de eventos
 * del microservicio Usuarios (event-driven).
 */
public class InicializarDatos {

    public static void main(String[] args) {
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            em.getTransaction().begin();

            // Usuarios simplificados de ejemplo
            em.persist(new UsuarioSimplificado(
                "usr-001", "juan@example.com", "Juan", "García López"));
            em.persist(new UsuarioSimplificado(
                "usr-002", "irene@example.com", "Irene", "Martínez Ruiz"));
            em.persist(new UsuarioSimplificado(
                "usr-003", "pedro@example.com", "Pedro", "Sánchez Díaz"));

            em.getTransaction().commit();
            System.out.println("Usuarios simplificados inicializados correctamente.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }
}