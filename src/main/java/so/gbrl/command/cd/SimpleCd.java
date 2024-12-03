package so.gbrl.command.cd;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.exceptions.DirectoryNotFoundException;
import so.gbrl.exceptions.SoException;
import so.gbrl.files.Directory;

import java.util.regex.Matcher;

public class SimpleCd extends CommandBase {
    public SimpleCd() {
        super("^cd( | \\.| ~)/(('[^']*'|[^'/abc]+))$");
    }

    public static Directory getCdDirectory(String input) {
        return switch (input) {
            case "~" -> FileSystemSimulator.ROOT;
            case "", "." -> FileSystemSimulator.CURRENT_DIRECTORY;
            default -> throw new SoException();
        };
    }

    public static Directory gotoDirectory(String directoryName, Directory directory) {
        for (Directory dir : directory.getDirectories()) {
            if (dir.name.equals(directoryName)) {
                FileSystemSimulator.CURRENT_DIRECTORY = dir;
                return dir;
            }
        }

        throw new DirectoryNotFoundException(directoryName);
    }

    @Override
    public void run(Matcher matcher) {
        String directoryName = matcher.group(2).replace("'", "");
        Directory directory = getCdDirectory(matcher.group(1).trim());
        gotoDirectory(directoryName, directory);
    }

    @Override
    public String help() {
        return "TODO";
    }
}
