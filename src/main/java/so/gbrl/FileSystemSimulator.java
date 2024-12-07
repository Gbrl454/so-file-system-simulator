package so.gbrl;

import so.gbrl.command.CommandBase;
import so.gbrl.exceptions.CommandNotFoundException;
import so.gbrl.exceptions.SoException;
import so.gbrl.files.Directory;
import so.gbrl.utils.IOUtil;
import so.gbrl.utils.ReflectionUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileSystemSimulator {
    public static Directory ROOT;
    public static Directory CURRENT_DIRECTORY;
    public static List<String> TERMINAL = new ArrayList<>();
    public static List<String> HISTORY_COMMANDS = new ArrayList<>();

    public static void print(String string) {
        TERMINAL.add(string);
    }

    public static void println(String string) {
        if (string != null) print(string + "\n");
        else print("\n");
    }

    private static void updateTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (String s : TERMINAL)
            System.out.print(s);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        IOUtil.updateMemory();
        CURRENT_DIRECTORY = ROOT;

        try (Scanner scanner = new Scanner(System.in)) {
            boolean isBreak = false;
            while (!isBreak) {
                try {
                    print(CURRENT_DIRECTORY.getPath() + " ");
                    updateTerminal();
                    String input = scanner.nextLine().trim();
                    if (!input.isBlank()) {
                        println(input);
                        for (String inputPart : input.split("&")) {
                            if (!executeInput(inputPart.trim())) {
                                isBreak = true;
                                break;
                            } else HISTORY_COMMANDS.add(input);
                        }
                    } else println(null);
                } catch (SoException e) {
                    println(e.getMessage());
                } catch (Exception e) {
                    println(SoException.UNEXPECTED_ERROR);
                }
            }
        }
    }

    private static boolean executeInput(String input) throws Exception {
        if (input.equalsIgnoreCase("exit")) return false;

        List<Class<?>> commandClasses = ReflectionUtil.getCommandClasses(input);
        if (commandClasses != null) {
            for (Class<?> clazz : commandClasses) {
                if (CommandBase.class.isAssignableFrom(clazz)) {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    boolean result = (boolean) clazz.getMethod("compare", String.class).invoke(instance, input);
                    if (result) return true;
                }
            }
            throw new CommandNotFoundException(input);
        }
        return false;
    }
}
