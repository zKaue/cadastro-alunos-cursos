    package cadastroalunocurso.app;

    import cadastroalunocurso.enumeration.OpcaoMenu;
    import cadastroalunocurso.model.Aluno;
    import cadastroalunocurso.model.Curso;
    import cadastroalunocurso.service.AlunoService;
    import cadastroalunocurso.service.CursoService;
    import cadastroalunocurso.service.MatriculaService;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;

    import static cadastroalunocurso.util.TecladoUtil.*;

    public class App {

        private static CursoService cursoService = new CursoService();
        private static AlunoService alunoService = new AlunoService();
        private static MatriculaService matriculaService = new MatriculaService();

        public static void main(String[] args) {
            exibirMenu();
        }

        public static void exibirMenu() {

            OpcaoMenu opcaoMenu;

            do {
                imprimirMenu();

                opcaoMenu = lerOpcaoMenu();

                if(opcaoMenu == OpcaoMenu.INVALIDA || opcaoMenu == null) {
                    System.out.print("\nOpção inválida! Digite um número de 0 a 8.");
                    continue;
                }

                switch (opcaoMenu) {
                    case CADASTRAR_ALUNO       -> cadastrarAluno();
                    case CADASTRAR_CURSO       -> cadastrarCurso();
                    case MATRICULAR_ALUNO      -> matricularAluno();
                    case CANCELAR_MATRICULA    -> cancelarMatricula();
                    case EXCLUIR_ALUNO         -> excluirAluno();
                    case EXCLUIR_CURSO         -> excluirCurso();
                    case LISTAR_ALUNOS         -> listarAlunos();
                    case LISTAR_CURSOS_ALUNOS  -> listarCursosEAlunos();
                    case SAIR                  -> System.exit(0);
                }
            } while (opcaoMenu != opcaoMenu.SAIR);
        }

        public static void imprimirMenu() {
            System.out.println("\n=========== MENU ===========");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Cadastrar Curso");
            System.out.println("3 - Matricular Aluno em Curso");
            System.out.println("4 - Cancelar Matrícula");
            System.out.println("5 - Excluir Aluno");
            System.out.println("6 - Excluir Curso");
            System.out.println("7 - Listar Alunos");
            System.out.println("8 - Listar Cursos e Alunos");
            System.out.println("0 - Sair");
            System.out.println("===========================");
        }

        private static void cadastrarAluno() {
            System.out.println("\n===== Cadastrar Aluno =====");
            String nome = lerString("Digite o nome do aluno: ");
            String cpf = lerString("Digite o CPF do aluno: ");

            Aluno aluno = new Aluno(nome, cpf);
            try {
                alunoService.cadastrar(aluno);
                System.out.println("Aluno cadastrado com sucesso!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private static void cadastrarCurso() {
            System.out.println("\n===== Cadastrar Curso =====");
            String nome = lerString("Digite o nome do curso: ");

            Curso curso = new Curso(nome);
            try {
                cursoService.cadastrar(curso);
                System.out.println("Curso cadastrado com sucesso!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public static void matricularAluno() {
            System.out.println("\n=== Matricular Aluno em Curso ===");
            Aluno aluno = selecionarAluno();
            if (aluno == null) return;

            //TRUE == SÓ MOSTRAR CURSOS QUE O ALUNO AINDA NÃO ESTÁ MATRICULADO
            Curso curso = selecionarCurso(aluno, true);
            if (curso == null) return;

            try {
                matriculaService.matricular(aluno, curso);
                System.out.println("Matrícula realizada com sucesso.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        private static void cancelarMatricula() {
            System.out.println("\n=== Cancelar Matrícula ===");
            Aluno aluno = selecionarAluno();
            if (aluno == null) return;

            //FALSE == SÓ MOSTRAR CURSOS QUE O ALUNO JÁ ESTÁ MATRICULADO
            Curso curso = selecionarCurso(aluno, false);
            if (curso == null) return;

            try {
                matriculaService.cancelar(aluno, curso);
                System.out.println("Matrícula cancelada com sucesso.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        private static void excluirAluno() {
            System.out.println("\n=== Excluir Aluno ===");
            Aluno alunoSelecionado = selecionarAluno();

            if (alunoSelecionado == null) {
                return;
            }

            boolean possuiMatriculas = matriculaService.estaMatriculadoEmAlgumCurso(alunoSelecionado);
            if (possuiMatriculas) {
                String confirmacao = lerString(
                        "ATENÇÃO: Este aluno está matriculado em curso(s). Digite 'S' para confirmar a exclusão ou qualquer outra tecla para cancelar: ").trim();
                if (!confirmacao.equalsIgnoreCase("S")) {
                    System.out.println("Exclusão cancelada.");
                    return;
                }
            }

            try {
                alunoService.remover(alunoSelecionado);
                System.out.println("Aluno de ID " + alunoSelecionado.getId() + " removido com sucesso.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        private static void excluirCurso() {
            System.out.println("\n=== Excluir Curso ===");
            Curso cursoSelecionado = selecionarCurso();
            if (cursoSelecionado == null) {
                return;
            }

            List<Aluno> alunosMatriculados = matriculaService.buscarAlunosPorCurso(cursoSelecionado);
            if (!alunosMatriculados.isEmpty()) {
                System.out.println("ATENÇÃO: Existem " + alunosMatriculados.size() + " aluno(s) matriculado(s) neste curso.");
                String confirmacao = lerString("Digite 'S' para confirmar a exclusão ou qualquer outra tecla para cancelar: ").trim();
                if (!confirmacao.equalsIgnoreCase("S")) {
                    System.out.println("Exclusão de curso cancelada.");
                    return;
                }
            }

            try {
                cursoService.remover(cursoSelecionado.getId());
                System.out.println("Curso excluído com sucesso.");
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }

        private static void listarAlunos() {
            System.out.println("\n=== Listar Alunos ===");
            List<Aluno> alunos = alunoService.retornaTodos();
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                return;
            }
            for (Aluno aluno : alunos) {
                System.out.printf("ID: %d | CPF: %s | Nome: %s%n", aluno.getId(), aluno.getCpf(), aluno.getNome());
            }
        }

        private static void listarCursosEAlunos() {
            System.out.println("\n=== Listar Cursos e Alunos Matriculados ===");
            List<Curso> cursos = cursoService.retornaTodos();
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso cadastrado.");
                return;
            }
            for (Curso curso : cursos) {
                List<Aluno> alunos = matriculaService.buscarAlunosPorCurso(curso);
                System.out.printf("%nCurso: %s (Matriculados: %d)%n", curso.getNome(), alunos.size());
                if (alunos.isEmpty()) {
                    System.out.println("Nenhum aluno matriculado.");
                } else {
                    for (Aluno aluno : alunos) {
                        System.out.printf("ID: %d | Aluno: %s | CPF: %s%n", aluno.getId(),aluno.getNome(), aluno.getCpf());
                    }
                }
            }
        }

        private static Aluno selecionarAluno() {
            List<Aluno> alunos = alunoService.retornaTodos();
            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno cadastrado.");
                return null;
            }
            System.out.println("Alunos disponíveis:");
            for (Aluno a : alunos) {
                System.out.printf("ID: %d | Nome: %s\n", a.getId(), a.getNome());
            }
            Integer id = lerInteiro("\nDigite o ID do aluno: ");
            if (id == null) {
                System.out.println("Entrada inválida. Operação cancelada.");
                return null;
            }
            Aluno aluno = alunoService.retornaPeloId(id);
            if (aluno == null) {
                System.out.println("Aluno não encontrado.");
            }
            return aluno;
        }

        private static Curso selecionarCurso(Aluno aluno, boolean paraMatricula) {
            //paraMatricula TRUE == CURSOS AINDA NÃO MATRICULADOS
            //paraMatricula FALSE == SÓ OS JÁ MATRICULADOS

            List<Curso> todosCursos = cursoService.retornaTodos();
            List<Curso> listaFiltrada = new ArrayList<>();

            for (Curso c : todosCursos) {
                boolean esta = matriculaService.estaMatriculado(aluno, c);
                if (paraMatricula ? !esta : esta) {
                    listaFiltrada.add(c);
                }
            }

            if (listaFiltrada.isEmpty()) {
                String msg = paraMatricula ? "Não há cursos disponíveis para matrícular esse aluno."
                                           : "Aluno não está matriculado em nenhum curso.";
                System.out.println(msg);
                return null;
            }

            System.out.println("\nCursos disponíveis:");
            for (Curso c : listaFiltrada) {
                System.out.printf("ID: %d | Nome: %s%n", c.getId(), c.getNome());
            }

            Integer id = lerInteiro("\nDigite o ID do curso: ");
            if (id == null) {
                System.out.println("Entrada inválida. Operação cancelada.");
                return null;
            }

            for (Curso c : listaFiltrada) {
                if (Objects.equals(c.getId(), id)) {
                    return c;
                }
            }

            System.out.println("Curso não encontrado.");
            return null;
        }

        private static Curso selecionarCurso() {

            List<Curso> todosCursos = cursoService.retornaTodos();
            if (todosCursos.isEmpty()) {
                System.out.println("Nenhum curso cadastrado.");
                return null;
            }

            System.out.println("\nCursos disponíveis:");
            for (Curso c : todosCursos) {
                System.out.printf("ID: %d | Nome: %s%n", c.getId(), c.getNome());
            }

            Integer id = lerInteiro("\nDigite o ID do curso: ");
            if (id == null) {
                System.out.println("Entrada inválida. Operação cancelada.");
                return null;
            }

            Curso curso = cursoService.retornaPeloId(id);
            if (curso == null) {
                System.out.println("Curso não encontrado.");
            }
            return curso;
        }



    }