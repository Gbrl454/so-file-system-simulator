package so.command;

import so.exceptions.SoException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(SoException.UNEXPECTED_ERROR);
            }





            return true;
        }
        return false;
    }

    public abstract void run(Matcher matcher);
}
