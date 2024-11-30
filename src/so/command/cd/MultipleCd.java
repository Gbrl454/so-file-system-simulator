package so.command.cd;

import so.command.CommandBase;
import so.files.Directory;

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
