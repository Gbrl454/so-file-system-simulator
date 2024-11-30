package so;

import so.command.CommandBase;
import so.exceptions.CommandNotFoundException;
import so.exceptions.SoException;
import so.files.Directory;
import so.files.Journal;
import so.utils.ReflectionUtil;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class FileSystemSimulator {
    public static final Directory ROOT = new Directory("ROOT", null);
    public static Directory CURRENT_DIRECTORY = ROOT;

    // Instância do Journal para registro de logs
    private static final Journal JOURNAL = new Journal();

    public static void main(String[] args) {
        new Directory("dir3", new Directory("dir2", new Directory("dir1", CURRENT_DIRECTORY)));

        Scanner scanner = new Scanner(System.in);
        JOURNAL.log("Sistema iniciado.");

        while (true) {
            try {
                System.out.print(CURRENT_DIRECTORY.getPath() + " ");
                String input = scanner.nextLine();

                if (Objects.equals("exit", input)) {
                    JOURNAL.log("Comando 'exit' recebido. Encerrando o sistema.");
                    break;
                }

                if (input != null && !input.isBlank()) {
                    for (String inputPart : input.split("&")) {
                        executeInput(inputPart.stripIndent());
                    }
                }
            } catch (SoException e) {
                JOURNAL.log("Erro de aplicação: " + e.getMessage());
                System.out.println(e.getMessage());
            } catch (Exception e) {
                JOURNAL.log("Erro inesperado: " + e.getMessage());
                e.printStackTrace();
                System.out.println(SoException.UNEXPECTED_ERROR);
            }
        }

        // Salvar o log ao sair
        String logPath = "src/logs/log.txt"; // Caminho do arquivo de log
        JOURNAL.salvarLog(logPath);
    }

    private static void executeInput(String input) throws Exception {
        JOURNAL.log("Executando comando: " + input);

        String commandsPackageName = "so.command." + input.split(" ")[0];
        URL commandsPackageUrl = ClassLoader.getSystemClassLoader().getResource(commandsPackageName.replace('.', '/'));

        if (commandsPackageUrl == null || commandsPackageUrl.getProtocol() == null) {
            JOURNAL.log("Comando não encontrado: " + input);
            throw new CommandNotFoundException(input);
        } else {
            if (commandsPackageUrl.getProtocol().equals("file")) {
                File directory = new File(commandsPackageUrl.toURI());
                if (directory.exists() && directory.isDirectory()) {
                    for (Class<?> clazz : ReflectionUtil.findClassesInDirectory(directory, commandsPackageName).stream().filter(it -> it.getSuperclass() == CommandBase.class).toList()) {
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        boolean result = (boolean) clazz.getMethod("compare", String.class).invoke(instance, input);
                        if (result) {
                            JOURNAL.log("Comando executado com sucesso: " + input);
                            return;
                        }
                    }
                }
                JOURNAL.log("Comando não encontrado no diretório: " + input);
                throw new CommandNotFoundException(input);
            }
        }
    }
}
