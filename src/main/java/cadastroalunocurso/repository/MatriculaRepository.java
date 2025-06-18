package cadastroalunocurso.repository;

import cadastroalunocurso.model.Aluno;
import cadastroalunocurso.model.Curso;
import cadastroalunocurso.model.Matricula;
import cadastroalunocurso.util.HibernateUtil;
import jakarta.persistence.*;

import java.util.List;

public class MatriculaRepository extends GenericRepository<Matricula> {

    public MatriculaRepository() {
        super(Matricula.class);
    }

    public boolean jaMatriculado(Aluno aluno, Curso curso) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            Long count = em.createQuery("""
                                SELECT COUNT(m) FROM Matricula m
                                WHERE m.aluno = :aluno AND m.curso = :curso
                            """, Long.class)
                    .setParameter("aluno", aluno)
                    .setParameter("curso", curso)
                    .getSingleResult();
            return count > 0;
        }
    }

    public List<Aluno> buscarAlunosPorCurso(Curso curso) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("""
                                SELECT m.aluno FROM Matricula m
                                WHERE m.curso = :curso
                            """, Aluno.class)
                    .setParameter("curso", curso)
                    .getResultList();
        }
    }

    public Matricula buscarPorAlunoECurso(Aluno aluno, Curso curso) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("""
                                SELECT m FROM Matricula m
                                WHERE m.aluno = :aluno AND m.curso = :curso
                            """, Matricula.class)
                    .setParameter("aluno", aluno)
                    .setParameter("curso", curso)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar matrícula: " + e.getMessage());
            return null;
        }
    }

    public boolean estaMatriculadoEmAlgumCurso(Aluno aluno) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            Long count = em.createQuery("""
                            SELECT COUNT(m) FROM Matricula m
                            WHERE m.aluno = :aluno
                        """, Long.class)
                    .setParameter("aluno", aluno)
                    .getSingleResult();
            return count > 0;
        }
    }

}
