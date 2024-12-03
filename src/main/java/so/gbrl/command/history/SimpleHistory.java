package so.gbrl.command.history;

import so.gbrl.command.CommandBase;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.HISTORY_COMMANDS;
import static so.gbrl.FileSystemSimulator.println;

public class SimpleHistory extends CommandBase {
    public SimpleHistory() {
        super("^history$");
    }

    @Override
    public void run(Matcher matcher) {
        for (int i = 0; i < HISTORY_COMMANDS.size(); i++) {
            println((i + 1) + " " + HISTORY_COMMANDS.get(i));
        }
    }

    @Override
    public String help() {
        return "TODO";
    }
}
