package so.gbrl.command.cat;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.exceptions.FileNotFoundException;
import so.gbrl.files.File;

import java.util.Objects;
import java.util.regex.Matcher;

public class SimpleCat extends CommandBase {
    public SimpleCat() {
        super("^cat +('[^']*'|[^' ]+)$");
    }

    @Override
    public void run(Matcher matcher) {
        String fileNameWithExtension = matcher.group(1).replace("'", "");
        for (File file : FileSystemSimulator.CURRENT_DIRECTORY.getFiles())
            if (Objects.equals(file.getFileNameWithExtension(), fileNameWithExtension)) {
                System.out.println(String.join("\n", file.content));
                return;
            }
        throw new FileNotFoundException(fileNameWithExtension);
    }
}
