package so.gbrl.files;

import java.util.List;

public class File extends FileSystemBase<String> {
    public String extension;

    public File(String name, String extension, List<String> content, Directory directory) {
        super(name, content);
        this.extension = extension;
        if (directory != null) directory.content.add(this);
        else throw new RuntimeException("Um arquivo deve ser criado dentro de uma pasta!");
    }
}
