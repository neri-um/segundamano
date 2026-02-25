package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {
    private static EntityManagerFactory entityManagerFactory;
    private static final ThreadLocal<EntityManager> entityManagerHolder;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("usuarios");
        entityManagerHolder = new ThreadLocal<>();
    }

    public static EntityManager getEntityManager() {
        EntityManager em = entityManagerHolder.get();
        if (em == null || !em.isOpen()) {
            em = entityManagerFactory.createEntityManager();
            entityManagerHolder.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
        EntityManager em = entityManagerHolder.get();
        if (em != null) {
            entityManagerHolder.set(null);
            em.close();
        }
    }
}