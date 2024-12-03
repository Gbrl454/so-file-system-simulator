package so.gbrl.files;

import java.util.List;

public class File extends FileSystemBase<String> {
    public String extension;

    public File(String name, String extension, List<String> content, Directory directory) {
        super(name);
        this.extension = extension;
        this.content = content;
        if (directory != null) directory.addContent(this);
        else throw new RuntimeException("Um arquivo deve ser criado dentro de uma pasta!");
    }

    public String getFileNameWithExtension() {
        return this.name + "." + this.extension;
    }

    @Override
    public int compareTo(FileSystemBase<?> o) {
        return 0;
    }
}
