package cadastroalunocurso.service;

import cadastroalunocurso.model.Curso;
import cadastroalunocurso.repository.CursoRepository;

import java.util.List;

public class CursoService {

    CursoRepository cursoRepository = new CursoRepository();

    public void cadastrar(Curso curso) throws Exception {

        validarNome(curso.getNome());

        if (cursoRepository.buscarPorNome(curso.getNome()) != null) {
            throw new Exception("O nome do curso já está cadastrado.");
        }
        cursoRepository.inserir(curso);
    }

    private void validarNome(String nome) throws Exception {
        if (nome == null || nome.isBlank()) {
            throw new Exception("Nome do curso não pode ser nulo ou em branco.");
        } else if (nome.length() > 50) {
            throw new Exception("Nome do curso deve ter no máximo 50 caracteres.");
        }
    }

    public void remover(int id) throws Exception {
        Curso curso = retornaPeloId(id);
        if (curso == null) {
            throw new Exception("Curso com ID " + id + " não encontrado.");
        }
        cursoRepository.excluir(id);
    }

    public List<Curso> retornaTodos(){
        return cursoRepository.retornaTodos();
    }

    public Curso retornaPeloId(int id){
        return cursoRepository.retornaPeloId(id);
    }

}
