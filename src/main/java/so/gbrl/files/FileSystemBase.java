package so.gbrl.files;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class FileSystemBase<T extends Comparable<T>> implements Comparable<FileSystemBase<?>> {
    public String name;
    public List<T> content;

    protected FileSystemBase(String name) {
        this.name = name;
        this.content = new ArrayList<>();
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

    public void addContent(T item) {
        this.content.add(item);
    }

    public boolean hasContent(T item) {
        return this.content.contains(item);
    }

    public List<T> listContent() {
        return this.content;
    }

    public String toJson() {
        return toJson(this);
    }
}
