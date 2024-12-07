package so.gbrl.command.rmdir;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.enums.Text;
import so.gbrl.files.Directory;
import so.gbrl.utils.IOUtil;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class SimpleRmdir extends CommandBase {
    public SimpleRmdir() {
        super("^rmdir (('[^']*'|[^'/ ]+))$");
    }

    public static boolean removeDirectory(String directoryName) {
        for (Directory dir : FileSystemSimulator.CURRENT_DIRECTORY.getDirectories()) {
            if (dir.name.equalsIgnoreCase(directoryName)) {
                if (!dir.getDirectories().isEmpty()) return false;
                FileSystemSimulator.CURRENT_DIRECTORY.removeContent(dir);
                return true;
            }
        }
        return false;
    }

    @Override
    public void run(Matcher matcher) {
        String directoryName = matcher.group(2).replace("'", "");

        if (removeDirectory(directoryName)) {
            println(Text.Style.NEGRITO.apply(Text.Color.VERDE.apply("[Success]")) +
                    Text.Color.VERDE.apply(" O diretório \"") +
                    Text.Style.NEGRITO.apply(Text.Color.VERDE.apply(directoryName)) +
                    Text.Color.VERDE.apply("\" foi removido com sucesso."));
        } else {
            println(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) +
                    Text.Color.VERMELHO.apply(" Não foi possível remover o diretório \"") +
                    Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(directoryName)) +
                    Text.Color.VERMELHO.apply("\". Verifique se ele existe e está vazio."));
        }

        IOUtil.refreshMemory();
    }

    @Override
    public String help() {
        return "Uso: rmdir <nome_do_diretorio>\n"
                + "Exemplo: rmdir exemplo\n"
                + "Remove um diretório vazio do diretório atual.";
    }
}
