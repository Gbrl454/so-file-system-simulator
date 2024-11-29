import command.CommandBase;
import enums.Text;
import utils.ReflectionUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class FileSystemSimulator {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (Objects.equals("exit", input)) break;
            executeInput(input);
        }
    }

    private static void executeInput(String input) throws URISyntaxException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String commandsPackageName = "command.sdf";
        URL commandsPackageUrl = ClassLoader.getSystemClassLoader().getResource(commandsPackageName.replace('.', '/'));

        if (commandsPackageUrl == null || commandsPackageUrl.getProtocol() == null)
            System.out.println(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) + Text.Color.VERMELHO.apply(" O comando \"") + Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(input)) + Text.Color.VERMELHO.apply("\" não foi identificado!"));
        else {
            if (commandsPackageUrl.getProtocol().equals("file")) {
                File directory = new File(commandsPackageUrl.toURI());
                if (directory.exists() && directory.isDirectory()) {
                    for (Class<?> clazz : ReflectionUtil.findClassesInDirectory(directory, commandsPackageName).stream().filter(it -> it.getSuperclass() == CommandBase.class).toList()) {
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        boolean result = (boolean) clazz.getMethod("compare", String.class).invoke(instance, input);
                        if (!result)
                            System.out.println(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) + Text.Color.VERMELHO.apply(" O comando \"") + Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(input)) + Text.Color.VERMELHO.apply("\" não foi identificado!"));
                    }
                }
            }
        }
    }


}
