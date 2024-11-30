package so.gbrl.command.ls;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.enums.Text;
import so.gbrl.files.Directory;
import so.gbrl.files.File;

import java.util.regex.Matcher;

public class SimpleLs extends CommandBase {
    public SimpleLs() {
        super("^ls$");
    }

    @Override
    public void run(Matcher matcher) {
        System.out.println(String.join("\t\t\t", FileSystemSimulator.CURRENT_DIRECTORY.content.stream().map(it -> switch (it) {
            case Directory dir -> Text.Color.AZUL.apply(dir.name);
            case File file -> file.name;
            default -> "";
        }).toList()));
    }
}
