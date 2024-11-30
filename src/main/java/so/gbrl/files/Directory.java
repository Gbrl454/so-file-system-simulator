package so.gbrl.files;

import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystemBase<FileSystemBase<?>> {
    private final Directory directoryParent;

    public Directory(String name, Directory directory) {
        super(name, new ArrayList<>());
        this.directoryParent = directory;
        if (directory != null) directory.content.add(this);
    }

    public List<Directory> getDirectories() {
        return content.stream().filter(it -> it instanceof Directory).map(it -> (Directory) it).toList();
    }

    public List<File> getFiles() {
        return content.stream().filter(it -> it instanceof File).map(it -> (File) it).toList();
    }

    public String getPath() {
        StringBuilder path = new StringBuilder();
        Directory directory = this;

        while (directory.directoryParent != null) {
            path.insert(0, directory.name + "/");
            directory = directory.directoryParent;
        }

        return "~/" + path;
    }

    public Directory getDirectoryParent() {
        return directoryParent;
    }
}
