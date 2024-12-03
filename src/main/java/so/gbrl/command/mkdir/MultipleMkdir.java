package so.gbrl.command.mkdir;

import so.gbrl.command.CommandBase;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class MultipleMkdir extends CommandBase {
    public MultipleMkdir() {
        super("^mkdir( -p)? (('[^']*'|[^'/ ]+)(/('[^']*'|[^'/ ]+))*)$");
    }

    @Override
    public void run(Matcher matcher) {
        println("sasfsdf");
    }

    @Override
    public String help() {
        return "TODO";
    }
}
