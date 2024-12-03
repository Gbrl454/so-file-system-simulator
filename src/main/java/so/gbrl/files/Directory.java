package so.gbrl.files;

import java.util.List;

public class Directory extends FileSystemBase<FileSystemBase<?>> {
    private final Directory directoryParent;

    public Directory(String name, Directory directory) {
        super(name);
        this.directoryParent = directory;
        if (directory != null) directory.addContent(this);
    }

    public List<Directory> getDirectories() {
        return this.listContent().stream().filter(Directory.class::isInstance).map(it -> (Directory) it).toList();
    }

    public List<File> getFiles() {
        return this.listContent().stream().filter(File.class::isInstance).map(it -> (File) it).toList();
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

    @Override
    public int compareTo(FileSystemBase<?> o) {
        return 0;
    }
}
