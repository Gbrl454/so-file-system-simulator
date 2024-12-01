package so.gbrl.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import so.gbrl.FileSystemSimulator;
import so.gbrl.exceptions.SoException;
import so.gbrl.files.Directory;
import so.gbrl.files.File;
import so.gbrl.files.FileSystemBase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class IOUtil {
    public static void updateMemory() throws IOException, URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource("memory.json").toURI());
        String json = Files.readString(path);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(json, Map.class);
        FileSystemSimulator.ROOT = (Directory) mapToFileSystem(map, null);
    }

    public static void updateMemoryFile() throws IOException, URISyntaxException {
        Path resourcePath = Paths.get("src/main/resources/memory.json");
        Files.writeString(resourcePath, FileSystemSimulator.ROOT.toJson());
        updateMemory();
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