package cadastroalunocurso.repository;

import cadastroalunocurso.model.Aluno;
import cadastroalunocurso.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class AlunoRepository extends GenericRepository<Aluno> {

    public AlunoRepository() {
        super(Aluno.class);
    }

    public Aluno retornaPeloCpf(String cpf) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT a FROM Aluno a WHERE a.cpf = :cpf", Aluno.class)
                    .setParameter("cpf", cpf)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    public boolean consultarCpf(String cpf) {
        return retornaPeloCpf(cpf) != null;
    }
}
