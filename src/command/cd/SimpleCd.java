package command.cd;

import command.CommandBase;

public class SimpleCd extends CommandBase {
    public SimpleCd() {
        super("^cd( | \\.| ~)/(('[^']*'|[^'abc]+))$");
    }

    @Override
    public void run() {
        System.out.println("dfdsfsdfsdf");
    }
}
