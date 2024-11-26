package files;

import java.util.ArrayList;

public class Directory extends FileSystemBase<FileSystemBase<?>> {
    public Directory(String name, Directory directory) {
        super(name, new ArrayList<>());
        if (directory != null) directory.content.add(this);
    }
}
