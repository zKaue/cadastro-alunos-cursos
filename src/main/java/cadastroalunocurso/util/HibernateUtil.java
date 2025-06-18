package cadastroalunocurso.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
