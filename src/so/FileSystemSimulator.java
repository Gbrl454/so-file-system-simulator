package so;

import so.command.CommandBase;
import so.exceptions.CommandNotFoundException;
import so.exceptions.SoException;
import so.files.Directory;
import so.files.Journal;
import so.utils.ReflectionUtil;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class FileSystemSimulator {
    public static final Directory ROOT = new Directory("ROOT", null);
    public static Directory CURRENT_DIRECTORY = ROOT;
    private static final Journal JOURNAL = new Journal();

    public static void main(String[] args) {
        new Directory("dir1", CURRENT_DIRECTORY);
        new Directory("dir2", CURRENT_DIRECTORY);
        new Directory("dir3", CURRENT_DIRECTORY);

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

    public static void save(){

    }

    private static Boolean executeInput(String input) throws Exception {
        if (input.equals("exit")) return false;
        String commandsPackageName = "so.command." + input.split(" ")[0];
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
