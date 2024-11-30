package so.command.cd;

import so.FileSystemSimulator;
import so.command.CommandBase;
import so.exceptions.DirectoryNotFoundException;
import so.exceptions.SoException;
import so.files.Directory;

import java.util.regex.Matcher;

public class SimpleCd extends CommandBase {
    public SimpleCd() {
        super("^cd( | \\.| ~)/(('[^']*'|[^'abc]+))$");
    }

    @Override
    public void run(Matcher matcher) {
        String directoryName = matcher.group(2).replace("'", "");

        Directory directory = switch (matcher.group(1).trim()) {
            case "~" -> FileSystemSimulator.ROOT;
            case "", "." -> FileSystemSimulator.CURRENT_DIRECTORY;
            default -> throw new SoException();
        };

        for (Directory dir : directory.getDirectories()) {
            if (dir.name.equals(directoryName)) {
                FileSystemSimulator.CURRENT_DIRECTORY = dir;
                return;
            }
        }

        throw new DirectoryNotFoundException(directoryName);
    }
}
