package so;

import so.command.CommandBase;
import so.exceptions.CommandNotFoundException;
import so.exceptions.SoException;
import so.files.Directory;
import so.utils.ReflectionUtil;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class FileSystemSimulator {
    public static final Directory ROOT = new Directory("ROOT", null);
    public static Directory CURRENT_DIRECTORY = ROOT;

    public static void main(String[] args) {
        Directory dir1 = new Directory("dir1", CURRENT_DIRECTORY);
        Directory dir2 = new Directory("dir2", dir1);
        Directory dir3 = new Directory("dir3", dir2);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(CURRENT_DIRECTORY.getPath() + " ");
                String input = scanner.nextLine();
                if (Objects.equals("exit", input)) break;
                if (input != null && !input.isBlank()) executeInput(input);
            } catch (SoException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(SoException.UNEXPECTED_ERROR);
            }
        }
    }

    private static void executeInput(String input) throws Exception {
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
                        if (!result) throw new CommandNotFoundException(input);
                    }
                }
            }
        }
    }
}
