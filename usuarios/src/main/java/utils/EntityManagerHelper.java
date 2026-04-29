package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EntityManagerHelper {
    private static EntityManagerFactory entityManagerFactory;
    private static final ThreadLocal<EntityManager> entityManagerHolder;

    static {
        Map<String, Object> overrides = new HashMap<>();

        String[] props = {
            "javax.persistence.jdbc.url",
            "javax.persistence.jdbc.user",
            "javax.persistence.jdbc.password",
            "javax.persistence.jdbc.driver"
        };

        for (String prop : props) {
            String envKey = prop.toUpperCase().replace('.', '_');
            String value = System.getenv(envKey);
            if (value != null && !value.isBlank()) {
                overrides.put(prop, value);
            }
        }

        entityManagerFactory = Persistence.createEntityManagerFactory("usuarios", overrides);
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