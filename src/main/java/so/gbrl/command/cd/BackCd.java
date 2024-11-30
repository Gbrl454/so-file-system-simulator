package so.gbrl.command.cd;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.exceptions.SoException;

import java.util.regex.Matcher;

public class BackCd extends CommandBase {
    public BackCd() {
        super("^cd ..$");
    }

    @Override
    public void run(Matcher matcher) {
        if (FileSystemSimulator.CURRENT_DIRECTORY.getDirectoryParent() == null)
            throw new SoException("Você já se encontra no diretório ROOT do sistema.");
        FileSystemSimulator.CURRENT_DIRECTORY = FileSystemSimulator.CURRENT_DIRECTORY.getDirectoryParent();
    }
}
