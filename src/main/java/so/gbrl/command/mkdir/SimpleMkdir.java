package so.gbrl.command.mkdir;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.enums.Text;
import so.gbrl.files.Directory;
import so.gbrl.utils.IOUtil;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class SimpleMkdir extends CommandBase {
    public SimpleMkdir() {
        super("^mkdir (('[^']*'|[^'/ ]+))$");
    }

    public static boolean makeDirectory(String directoryName) {
        for (Directory dir : FileSystemSimulator.CURRENT_DIRECTORY.getDirectories()) {
            if (dir.name.equalsIgnoreCase(directoryName)) return false;
        }

        new Directory(directoryName, FileSystemSimulator.CURRENT_DIRECTORY);
        return true;
    }

    @Override
    public void run(Matcher matcher) {
        String directoryName = matcher.group(2).replace("'", "");
        if (!makeDirectory(directoryName))
            println(Text.Style.NEGRITO.apply(Text.Color.AMARELO.apply("[Warn]")) + Text.Color.AMARELO.apply(" o diretorio \"") + Text.Style.NEGRITO.apply(Text.Color.AMARELO.apply(directoryName)) + Text.Color.AMARELO.apply("\" já existe!"));
        IOUtil.refreshMemory();
    }

    @Override
    public String help() {
        return "TODO";
    }
}
