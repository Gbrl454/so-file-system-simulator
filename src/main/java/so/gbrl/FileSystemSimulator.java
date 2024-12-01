package so.gbrl;

import so.gbrl.command.CommandBase;
import so.gbrl.exceptions.CommandNotFoundException;
import so.gbrl.exceptions.SoException;
import so.gbrl.files.Directory;
import so.gbrl.files.Journal;
import so.gbrl.utils.IOUtil;
import so.gbrl.utils.ReflectionUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class FileSystemSimulator {
    private static final Journal JOURNAL = new Journal();
    public static Directory ROOT;
    public static Directory CURRENT_DIRECTORY;

    public static void main(String[] args) throws IOException, URISyntaxException {
        IOUtil.updateMemory();
        CURRENT_DIRECTORY = ROOT;

        Scanner scanner = new Scanner(System.in);
        boolean isBreak = false;
        while (!isBreak) {
            try {
                System.out.print(CURRENT_DIRECTORY.getPath() + " ");
                String input = scanner.nextLine();
                if (input != null && !input.isBlank()) {
                    for (String inputPart : input.split("&")) {
                        if (!executeInput(inputPart.stripIndent())) {
                            isBreak = true;
                            break;
                        }
                    }
                }
            } catch (SoException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(SoException.UNEXPECTED_ERROR);
            }
        }
    }

    private static Boolean executeInput(String input) throws Exception {
        if (input.equals("exit")) return false;
        String commandsPackageName = "so.gbrl.command." + input.split(" ")[0];
        URL commandsPackageUrl = ClassLoader.getSystemClassLoader().getResource(commandsPackageName.replace('.', '/'));

        if (commandsPackageUrl == null || commandsPackageUrl.getProtocol() == null)
            throw new CommandNotFoundException(input);
        else {
            if (commandsPackageUrl.getProtocol().equals("file")) {
                File directory = new File(commandsPackageUrl.toURI());
                if (directory.exists() && directory.isDirectory()) {
                    for (Class<?> clazz : ReflectionUtil.findClassesInDirectory(directory, commandsPackageName).stream().filter(it -> it.getSuperclass() == CommandBase.class).toList()) {
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        boolean result = (boolean) clazz.getMethod("compare", String.class).invoke(instance, input);
                        if (result) return true;
                    }
                }
                throw new CommandNotFoundException(input);
            }
        }
        return false;
    }
}
