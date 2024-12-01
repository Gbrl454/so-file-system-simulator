package so.gbrl.files;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class FileSystemBase<T extends Comparable<T>> implements Comparable<FileSystemBase<?>> {
    public final String name;
    public final AVLTree<T> content;

    protected FileSystemBase(String name) {
        this.name = name;
        this.content = new AVLTree<>();
    }

    public void addContent(T item) {
        content.insert(item);
    }

    public boolean hasContent(T item) {
        return content.contains(item);
    }

    public List<T> listContent() {
        return content.toList();
    }

    private static String toJson(Object obj) {
        if (obj == null) return null;
        Class<?> clazz = obj.getClass();

        List<String> fields = new ArrayList<>();

        for (Field field : clazz.getFields()) {
            field.setAccessible(true);
            try {
                Object valueObj = field.get(obj);
                String value = switch (valueObj) {
                    case String s -> "\"" + s + "\"";
                    case Number n -> n.toString();
                    case List<?> list ->
                            list.stream().map(item -> item instanceof String ? "\"" + item + "\"" : toJson(item)).toList().toString();
                    case Directory dir -> "{" + toJson(dir) + "}";
                    case File file -> "{" + toJson(file) + "}";
                    case null, default -> "null";
                };
                fields.add("\"" + field.getName() + "\":" + value);
            } catch (IllegalAccessException e) {
                return null;
            }
        }

        return "{\"type\":\"" + clazz.getName() + "\"," + String.join(",", fields) + "}";
    }

    public String toJson() {
        return toJson(this);
    }
}
