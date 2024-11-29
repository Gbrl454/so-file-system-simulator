package command;

import java.util.regex.Pattern;

public abstract class CommandBase {
    private final String REGEX;

    public CommandBase(String regex) {
        this.REGEX = regex;
    }

    public boolean compare(String input) {
        if (Pattern.compile(REGEX).matcher(input.trim()).matches()) {
            run();
            return true;
        }
        return false;
    }

    public abstract void run();
}
