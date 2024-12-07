package so.gbrl.command.rm;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.enums.Text;
import so.gbrl.files.File;
import so.gbrl.utils.IOUtil;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class SimpleRm extends CommandBase {

    public SimpleRm() {
        super("^rm ([^/ ]+)$");
    }

    @Override
    public void run(Matcher matcher) {
        String filename = matcher.group(1);

        File fileToRemove = findFile(filename);

        if (fileToRemove == null) {
            println(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) +
                    Text.Color.VERMELHO.apply(" O arquivo \"") +
                    Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(filename)) +
                    Text.Color.VERMELHO.apply("\" não foi encontrado no diretório atual."));
            return;
        }

        FileSystemSimulator.CURRENT_DIRECTORY.removeContent(fileToRemove);

        println(Text.Style.NEGRITO.apply(Text.Color.VERDE.apply("[Success]")) +
                Text.Color.VERDE.apply(" O arquivo \"") +
                Text.Style.NEGRITO.apply(Text.Color.VERDE.apply(filename)) +
                Text.Color.VERDE.apply("\" foi removido com sucesso."));

        IOUtil.refreshMemory();
    }

    @Override
    public String help() {
        return "Uso: rm <filename>\n"
                + "Exemplo: rm exemplo.txt\n"
                + "Remove o arquivo especificado do diretório atual.";
    }

    private File findFile(String filename) {
        return FileSystemSimulator.CURRENT_DIRECTORY.getFiles().stream()
                .filter(file -> (file.name + "." + file.extension).equals(filename))
                .findFirst()
                .orElse(null);
    }
}
