package cadastroalunocurso.service;

import cadastroalunocurso.model.Aluno;
import cadastroalunocurso.model.Curso;
import cadastroalunocurso.model.Matricula;
import cadastroalunocurso.repository.MatriculaRepository;

import java.util.List;

public class MatriculaService {

    MatriculaRepository matriculaRepository = new MatriculaRepository();

    public void matricular(Aluno aluno, Curso curso) throws Exception {
        if (matriculaRepository.jaMatriculado(aluno, curso)) {
            throw new Exception("Este aluno já está matriculado neste curso.");
        }

        Matricula matricula = new Matricula(aluno, curso);
        matriculaRepository.inserir(matricula);
    }

    public void cancelar(Aluno aluno, Curso curso) throws Exception {
        Matricula matricula = matriculaRepository.buscarPorAlunoECurso(aluno, curso);
        if (matricula == null) {
            throw new Exception("Este aluno não está matriculado neste curso.");
        }
        matriculaRepository.excluir(matricula.getId());
    }

    public List<Aluno> buscarAlunosPorCurso(Curso curso) {
        return matriculaRepository.buscarAlunosPorCurso(curso);
    }

    public boolean estaMatriculadoEmAlgumCurso(Aluno aluno) {
        return matriculaRepository.estaMatriculadoEmAlgumCurso(aluno);
    }

    public boolean estaMatriculado(Aluno aluno, Curso curso) {
        return matriculaRepository.jaMatriculado(aluno, curso);
    }

}
