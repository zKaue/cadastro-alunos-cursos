package cadastroalunocurso.service;

import cadastroalunocurso.model.Aluno;
import cadastroalunocurso.repository.AlunoRepository;

import java.util.List;

public class AlunoService {

    AlunoRepository alunoRepository = new AlunoRepository();

    public void cadastrar(Aluno aluno) throws Exception {

        validarNome(aluno.getNome());
        validarCpf(aluno.getCpf());

        if (alunoRepository.consultarCpf(aluno.getCpf())) {
            throw new Exception("O CPF informado já está cadastrado.");
        }
        alunoRepository.inserir(aluno);
    }

    public void remover(Aluno aluno) throws Exception {

        if (aluno == null) {
            throw new Exception("Aluno não existe");
        }

        alunoRepository.excluir(aluno.getId());
    }

    private void validarNome(String nome) throws Exception {
        if (nome == null) {
            throw new Exception("Nome não pode ser nulo.");
        } else if (nome.trim().isBlank()) {
            throw new Exception("Nome não pode estar em branco.");
        } else if (nome.length() > 100) {
            throw new Exception("Limite de caracteres ultrapassado (máx. 100 caracteres).");
        } else if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s'-]+$")) {
            throw new Exception("Nome inválido! Não use números ou símbolos especiais.");
        }
    }

    private void validarCpf(String cpf) throws Exception {
        if (cpf.trim().isBlank()) {
            throw new Exception("CPF não pode estar em branco.");
        } else if (!cpf.matches("^[0-9]{11}$")) {
            throw new Exception("O CPF deve conter exatamente 11 dígitos numéricos.");
        }
    }

    public List<Aluno> retornaTodos() {
        return alunoRepository.retornaTodos();
    }

    public Aluno retornaPeloId(Integer id) {
        return alunoRepository.retornaPeloId(id);
    }

}

