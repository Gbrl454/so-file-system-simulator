package so.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
    public static List<Class<?>> findClassesInDirectory(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) classes.addAll(findClassesInDirectory(file, packageName + "." + file.getName()));
                else if (file.getName().endsWith(".class")) classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
