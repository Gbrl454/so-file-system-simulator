package so.gbrl.command.clear;

import so.gbrl.command.CommandBase;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.TERMINAL;

public class SimpleClear extends CommandBase {
    public SimpleClear() {
        super("^clear$");
    }

    @Override
    public void run(Matcher matcher) {
        TERMINAL.clear();
    }

    @Override
    public String help() {
        return "TODO";
    }
}
