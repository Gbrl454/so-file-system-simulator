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
import java.util.List;
import java.util.Scanner;

public class FileSystemSimulator {
    private static final Journal JOURNAL = new Journal();
    public static Directory ROOT;
    public static Directory CURRENT_DIRECTORY;

    public static void main(String[] args) throws IOException, URISyntaxException {
        IOUtil.updateMemory();
        CURRENT_DIRECTORY = ROOT;

        try (Scanner scanner = new Scanner(System.in)) {
            boolean isBreak = false;
            while (!isBreak) {
                try {
                    System.out.print(CURRENT_DIRECTORY.getPath() + " ");
                    String input = scanner.nextLine().trim();
                    if (!input.isEmpty()) {
                        // Process each input part separated by '&'
                        for (String inputPart : input.split("&")) {
                            if (!executeInput(inputPart.trim())) {
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
    }

    private static boolean executeInput(String input) throws Exception {
        if (input.equalsIgnoreCase("exit")) return false;

        String commandName = input.split(" ")[0];
        String commandsPackageName = "so.gbrl.command." + commandName;
        URL commandsPackageUrl = ClassLoader.getSystemClassLoader().getResource(commandsPackageName.replace('.', '/'));

        if (commandsPackageUrl == null || commandsPackageUrl.getProtocol() == null) {
            throw new CommandNotFoundException(input);
        }

        if (commandsPackageUrl.getProtocol().equals("file")) {
            File directory = new File(commandsPackageUrl.toURI());
            if (directory.exists() && directory.isDirectory()) {
                List<Class<?>> commandClasses = ReflectionUtil.findClassesInDirectory(directory, commandsPackageName);
                for (Class<?> clazz : commandClasses) {
                    if (CommandBase.class.isAssignableFrom(clazz)) {
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        boolean result = (boolean) clazz.getMethod("compare", String.class).invoke(instance, input);
                        if (result) return true;
                    }
                }
            }
            throw new CommandNotFoundException(input);
        }
        return false;
    }
}
