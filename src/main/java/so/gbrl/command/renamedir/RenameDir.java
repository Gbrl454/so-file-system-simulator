package so.gbrl.command.renamedir;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.enums.Text;
import so.gbrl.files.Directory;
import so.gbrl.utils.IOUtil;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class RenameDir extends CommandBase {
    public RenameDir() {
        super("^renamedir ('[^']*'|[^'/ ]+) ('[^']*'|[^'/ ]+)$");
    }

    @Override
    public void run(Matcher matcher) {
        String oldName = matcher.group(1).replace("'", "");
        String newName = matcher.group(2).replace("'", "");

        Directory currentDirectory = FileSystemSimulator.CURRENT_DIRECTORY;

        Directory dirToRename = null;
        for (Directory dir : currentDirectory.getDirectories()) {
            if (dir.name.equalsIgnoreCase(oldName)) {
                dirToRename = dir;
                break;
            }
        }

        if (dirToRename == null) {
            println(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) +
                    Text.Color.VERMELHO.apply(" O diretório \"") +
                    Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(oldName)) +
                    Text.Color.VERMELHO.apply("\" não foi encontrado no diretório atual."));
            return;
        }

        for (Directory dir : currentDirectory.getDirectories()) {
            if (dir.name.equalsIgnoreCase(newName)) {
                println(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) +
                        Text.Color.VERMELHO.apply(" Já existe um diretório com o nome \"") +
                        Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(newName)) +
                        Text.Color.VERMELHO.apply("\" no diretório atual."));
                return;
            }
        }

        dirToRename.name = newName;
        println(Text.Style.NEGRITO.apply(Text.Color.VERDE.apply("[Success]")) +
                Text.Color.VERDE.apply(" O diretório \"") +
                Text.Style.NEGRITO.apply(Text.Color.VERDE.apply(oldName)) +
                Text.Color.VERDE.apply("\" foi renomeado para \"") +
                Text.Style.NEGRITO.apply(Text.Color.VERDE.apply(newName)) +
                Text.Color.VERDE.apply("\" com sucesso."));

        IOUtil.refreshMemory();
    }

    @Override
    public String help() {
        return "Uso: renamedir <oldname> <newname>\n"
                + "Renomeia um diretório no diretório atual.";
    }
}
