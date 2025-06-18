package cadastroalunocurso.repository;

import java.util.List;

import cadastroalunocurso.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class GenericRepository<T> {

    private final Class<T> entityClass;

    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void inserir(T objeto) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(objeto);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void excluir(Object id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void atualizar(T objeto) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(objeto);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public T retornaPeloId(Object id) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.find(entityClass, id);
        }
    }

    public List<T> retornaTodos() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("from " + entityClass.getSimpleName(), entityClass)
                    .getResultList();
        }
    }

}
