package files;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FileSystemBase<T> {

    private Journal journal;

    public Journal FileSystemSimulator() {
        return this.journal = new Journal();
    }

    private Map<String, File> files = new HashMap<>();

    public void criarArquivo(String nome) {
        if (!files.containsKey(nome)) {
            files.put(nome, new File(nome));
            System.out.println("Arquivo '" + nome + "' criado com sucesso.");
        } else {
            System.out.println("Erro: O arquivo já existe.");
        }
    }

    // Apagar um arquivo
    public void apagarArquivo(String nome) {
        if (files.containsKey(nome)) {
            files.remove(nome);
            System.out.println("Arquivo '" + nome + "' apagado com sucesso.");
        } else {
            System.out.println("Erro: O arquivo não foi encontrado.");
        }
    }

    // Renomear um arquivo
    public void renomearArquivo(String nomeAntigo, String nomeNovo) {
        if (files.containsKey(nomeAntigo)) {
            File file = files.remove(nomeAntigo);
            file.setName(nomeNovo);
            files.put(nomeNovo, file);
            System.out.println("Arquivo '" + nomeAntigo + "' renomeado para '" + nomeNovo + "'.");
        } else {
            System.out.println("Erro: O arquivo '" + nomeAntigo + "' não foi encontrado.");
        }
    }

    // Listar todos os arquivos
    public void listarArquivos() {
        if (files.isEmpty()) {
            System.out.println("Nenhum arquivo encontrado.");
        } else {
            System.out.println("Arquivos no sistema:");
            for (File file : files.values()) {
                System.out.println("  " + file);
            }
        }
    }

    public final String name;
    public final List<T> content;

    protected FileSystemBase(String name, List<T> content) {
        this.name = name;
        this.content = content;
    }

    private static String toJson(Object obj) {
        if (obj == null)
            return null;
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
                        "[" + list.stream().map(item -> item instanceof String ? "\"" + item + "\"" : toJson(item))
                                .toList() + "]";
                    case Directory dir -> "{" + toJson(dir) + "}";
                    case File file -> "{" + toJson(file) + "}";
                    case null, default -> "null";
                };
                fields.add("\"" + field.getName() + "\":" + value);
            } catch (IllegalAccessException e) {
                return null;
            }
        }

        return "{" + String.join(",", fields) + "}";
    }

    public String toJson() {
        return toJson(this);
    }
}
