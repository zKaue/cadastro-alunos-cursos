package cadastroalunocurso.enumeration;

public enum OpcaoMenu {
    CADASTRAR_ALUNO(1, "Opção selecionada: Cadastrar Aluno"),
    CADASTRAR_CURSO(2, "Opção selecionada: Cadastrar Curso"),
    MATRICULAR_ALUNO(3, "Opção selecionada: Matricular Aluno"),
    CANCELAR_MATRICULA(4, "Opção selecionada: Cancelar Matricula"),
    EXCLUIR_ALUNO(5, "Opção selecionada: Excluir Aluno"),
    EXCLUIR_CURSO(6, "Opção selecionada: Excluir Curso"),
    LISTAR_ALUNOS(7, "Opção selecionada: Listar Alunos"),
    LISTAR_CURSOS_ALUNOS(8, "Opção selecionada: Listar Alunos e Cursos"),
    SAIR(0, "Programa encerrado"),
    INVALIDA(-1, "Opção inválida");

    private final int codigo;
    private final String descricao;

    OpcaoMenu(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static OpcaoMenu obterPorCodigo(int codigo) {
        for (OpcaoMenu opcao : OpcaoMenu.values()) {
            if (opcao.getCodigo() == codigo) {
                return opcao;
            }
        }
        return null;
    }

}
