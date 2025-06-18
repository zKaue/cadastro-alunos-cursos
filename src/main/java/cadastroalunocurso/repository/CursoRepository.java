package cadastroalunocurso.repository;

import cadastroalunocurso.model.Curso;
import cadastroalunocurso.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class CursoRepository extends GenericRepository<Curso> {

    public CursoRepository() {
        super(Curso.class);
    }

    public Curso buscarPorNome(String nome) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT c FROM Curso c WHERE c.nome = :nome", Curso.class)
                    .setParameter("nome", nome)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }
}
