package so.gbrl.command;

import so.gbrl.exceptions.SoException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static so.gbrl.FileSystemSimulator.println;

public abstract class CommandBase {
    private final String REGEX;

    public CommandBase(String regex) {
        this.REGEX = regex;
    }

    public boolean compare(String input) {
        Matcher matcher = Pattern.compile(REGEX).matcher(input.trim());
        if (matcher.matches()) {
            try {
                run(matcher);
            } catch (SoException e) {
                println(e.getMessage());
            } catch (Exception e) {
                println(SoException.UNEXPECTED_ERROR);
            }
            return true;
        }
        return false;
    }

    public abstract void run(Matcher matcher);

    public abstract String help();
}
