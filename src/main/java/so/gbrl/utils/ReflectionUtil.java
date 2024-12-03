package so.gbrl.utils;

import so.gbrl.exceptions.CommandNotFoundException;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
    public static List<Class<?>> findClassesInDirectory(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory())
                    classes.addAll(findClassesInDirectory(file, packageName + "." + file.getName()));
                else if (file.getName().endsWith(".class"))
                    classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static List<Class<?>> getCommandClasses(String input) throws ClassNotFoundException {
        String commandsPackageName;
        if (input == null) commandsPackageName = "so.gbrl.command";
        else {
            String commandName = input.split(" ")[0];
            commandsPackageName = "so.gbrl.command" + ((commandName == null) ? "" : "." + commandName);
        }

        URL resource = Thread.currentThread().getContextClassLoader().getResource(commandsPackageName.replace('.', '/'));
        if (resource == null || resource.getProtocol() == null) throw new CommandNotFoundException(input);

        File directory = new File(resource.getFile());

        if (directory.exists() && directory.isDirectory())
            return ReflectionUtil.findClassesInDirectory(directory, commandsPackageName);
        return null;
    }
}
