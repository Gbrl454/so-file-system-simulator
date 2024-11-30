package so.gbrl.command.cd;

import so.gbrl.command.CommandBase;
import so.gbrl.files.Directory;

import java.util.regex.Matcher;

public class MultipleCd extends CommandBase {
    public MultipleCd() {
        super("^cd( | \\.| ~)/(('[^']*'|[^'/abc]+)(/('[^']*'|[^'/abc]+))+)$");
    }

    @Override
    public void run(Matcher matcher) {
        Directory directory = SimpleCd.getCdDirectory(matcher.group(1).trim());
        for (String dir : matcher.group(2).split("/")) directory = SimpleCd.gotoDirectory(dir, directory);
    }
}
