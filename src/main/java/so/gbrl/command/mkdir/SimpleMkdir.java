package so.gbrl.command.mkdir;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.enums.Text;
import so.gbrl.files.Directory;
import so.gbrl.utils.IOUtil;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class SimpleMkdir extends CommandBase {
    public SimpleMkdir() {
        super("^mkdir ('[^']*'|[^'/ ]+)$");
    }

    public static boolean makeDirectory(String directoryName) {
        for (Directory dir : FileSystemSimulator.CURRENT_DIRECTORY.getDirectories()) {
            if (dir.name.equalsIgnoreCase(directoryName)) return false;
        }

        new Directory(directoryName, FileSystemSimulator.CURRENT_DIRECTORY);
        return true;
    }

    @Override
    public void run(Matcher matcher) {
        String directoryName = matcher.group(1).replace("'", "");

        if (!makeDirectory(directoryName)) {
            println(Text.Style.NEGRITO.apply(Text.Color.AMARELO.apply("[Warn]")) +
                    Text.Color.AMARELO.apply(" O diretório \"") +
                    Text.Style.NEGRITO.apply(Text.Color.AMARELO.apply(directoryName)) +
                    Text.Color.AMARELO.apply("\" já existe!"));
        } else {
            println(Text.Style.NEGRITO.apply(Text.Color.VERDE.apply("[Success]")) +
                    Text.Color.VERDE.apply(" O diretório \"") +
                    Text.Style.NEGRITO.apply(Text.Color.VERDE.apply(directoryName)) +
                    Text.Color.VERDE.apply("\" foi criado com sucesso."));
        }

        IOUtil.refreshMemory();
    }

    @Override
    public String help() {
        return "Uso: mkdir <nome_do_diretorio>\n"
                + "Exemplo: mkdir pasta1\n"
                + "Cria um único diretório no diretório atual.";
    }
}
