package files;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class Directory {
    private String name; // Nome do diretório
    private Map<String, Directory> subDirectories; // Subdiretórios
    private Map<String, File> files; // Arquivos no diretório

    // Construtor
    public Directory(String name) {
        this.name = name;
        this.subDirectories = new HashMap<>();
        this.files = new HashMap<>();
    }

    // Retorna o nome do diretório
    public String getName() {
        return name;
    }

    // Define o nome do diretório
    public void setName(String name) {
        this.name = name;
    }

    // Adiciona um subdiretório
    public boolean addSubDirectory(String subDirectoryName) {
        if (!subDirectories.containsKey(subDirectoryName)) {
            subDirectories.put(subDirectoryName, new Directory(subDirectoryName));
            return true;
        }
        return false; // Já existe um subdiretório com esse nome
    }

    // Remove um subdiretório
    public boolean removeSubDirectory(String subDirectoryName) {
        return subDirectories.remove(subDirectoryName) != null;
    }

    // Adiciona um arquivo
    public boolean addFile(String fileName) {
        if (!files.containsKey(fileName)) {
            files.put(fileName, new File(fileName));
            return true;
        }
        return false; // Já existe um arquivo com esse nome
    }

    // Remove um arquivo
    public boolean removeFile(String fileName) {
        return files.remove(fileName) != null;
    }

    // Renomeia um arquivo ou subdiretório
    public boolean rename(String oldName, String newName) {
        if (files.containsKey(oldName)) {
            File file = files.remove(oldName);
            file.setName(newName);
            files.put(newName, file);
            return true;
        } else if (subDirectories.containsKey(oldName)) {
            Directory dir = subDirectories.remove(oldName);
            dir.setName(newName);
            subDirectories.put(newName, dir);
            return true;
        }
        return false; // Nenhum arquivo ou subdiretório encontrado com o nome antigo
    }

    // Lista o conteúdo do diretório
    public void listContents() {
        System.out.println("Conteúdo do diretório: " + name);
        if (files.isEmpty() && subDirectories.isEmpty()) {
            System.out.println("  [Vazio]");
            return;
        }

        for (String dirName : subDirectories.keySet()) {
            System.out.println("  [DIR] " + dirName);
        }
        for (String fileName : files.keySet()) {
            System.out.println("  [FILE] " + fileName);
        }
    }

    // Obter um subdiretório
    public Directory getSubDirectory(String subDirectoryName) {
        return subDirectories.get(subDirectoryName);
    }

    // Verificar se um arquivo existe
    public boolean hasFile(String fileName) {
        return files.containsKey(fileName);
    }

    // Verificar se um subdiretório existe
    public boolean hasSubDirectory(String subDirectoryName) {
        return subDirectories.containsKey(subDirectoryName);
    }
}
