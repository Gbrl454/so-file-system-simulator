package so.gbrl.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import so.gbrl.FileSystemSimulator;
import so.gbrl.exceptions.SoException;
import so.gbrl.files.Directory;
import so.gbrl.files.File;
import so.gbrl.files.FileSystemBase;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import static so.gbrl.FileSystemSimulator.println;

@SuppressWarnings("unchecked")
public class IOUtil {
    private static final String MEMORY_FILE = "src/main/resources/memory.json";

    public static void updateMemory() throws IOException {
        Path path = Path.of(MEMORY_FILE);
        if (!Files.exists(path)) {
            FileSystemSimulator.ROOT =  new Directory("ROOT", null);
            updateMemoryFile();
        } else {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            FileSystemSimulator.ROOT  = (Directory) mapToFileSystem(map, null);
        }
    }

    public static void updateMemoryFile() throws IOException {
        Path path = Path.of(MEMORY_FILE);
        Files.createDirectories(path.getParent());

        Files.writeString(path,
                FileSystemSimulator.ROOT.toJson(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void refreshMemory() {
        try {
            updateMemoryFile();
        } catch (SoException e) {
            println(e.getMessage());
        } catch (Exception e) {
            println(SoException.UNEXPECTED_ERROR);
        }
    }

    public static FileSystemBase<?> mapToFileSystem(Map<String, Object> map, Directory directory) {
        String type = (String) map.get("type");
        if (type == null) throw new SoException("Tipo de arquivo não identificado");

        switch (type) {
            case "so.gbrl.files.Directory" -> {
                Directory newDirectory = new Directory(map.get("name").toString(), directory);
                for (Map<String, Object> childMap : mapToContent(map, Map.class))
                    mapToFileSystem(childMap, newDirectory);
                return newDirectory;
            }
            case "so.gbrl.files.File" ->
                    new File(map.get("name").toString(), map.get("extension").toString(), mapToContent(map, String.class), directory);
            default -> throw new SoException("Tipo de arquivo desconhecido: " + type);
        }
        return null;
    }

    private static <T> List<T> mapToContent(Map<String, Object> map, Class<T> type) {
        Object contentObj = map.get("content");
        if (contentObj instanceof List<?> contentList) {
            for (Object item : contentList) {
                if (!type.isInstance(item)) throw new SoException("O conteúdo contém itens de tipo inválido: " + item);
            }
            return (List<T>) contentList;
        } else if (contentObj != null) throw new SoException("O campo 'content' não é uma lista.");
        return List.of();
    }
}