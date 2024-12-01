package so.gbrl.command.cd;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;

import java.util.regex.Matcher;

public class BackRootCd extends CommandBase {
    public BackRootCd() {
        super("^cd$");
    }

    @Override
    public void run(Matcher matcher) {
        FileSystemSimulator.CURRENT_DIRECTORY = FileSystemSimulator.ROOT;
    }
}
