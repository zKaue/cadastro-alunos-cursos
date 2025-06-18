package cadastroalunocurso.util;

import cadastroalunocurso.enumeration.OpcaoMenu;

import java.util.Scanner;

public class TecladoUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public static Integer lerInteiro(String mensagem) {
        System.out.print(mensagem);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static OpcaoMenu lerOpcaoMenu() {
        Integer codigo = lerInteiro("Escolha a opção que deseja: ");
        if (codigo == null) {
            return OpcaoMenu.INVALIDA;
        }
        return OpcaoMenu.obterPorCodigo(codigo);
    }

}
