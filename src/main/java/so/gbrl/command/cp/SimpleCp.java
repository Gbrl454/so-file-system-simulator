package so.gbrl.command.cp;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.enums.Text;
import so.gbrl.files.Directory;
import so.gbrl.files.File;
import so.gbrl.utils.IOUtil;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class SimpleCp extends CommandBase {

    public SimpleCp() {
        super("^cp ([^/ ]+) ([^/ ]+)$");
    }

    @Override
    public void run(Matcher matcher) {
        String filename = matcher.group(1);
        String path = matcher.group(2);

        File fileToCopy = findFile(filename);
        Directory targetDirectory = findDirectory(path);

        if (fileToCopy == null) {
            println(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) +
                    Text.Color.VERMELHO.apply(" O arquivo \"") +
                    Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(filename)) +
                    Text.Color.VERMELHO.apply("\" não foi encontrado."));
            return;
        }

        if (targetDirectory == null) {
            println(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) +
                    Text.Color.VERMELHO.apply(" O diretório \"") +
                    Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(path)) +
                    Text.Color.VERMELHO.apply("\" não foi encontrado."));
            return;
        }

        copyFile(fileToCopy, targetDirectory);

        println(Text.Style.NEGRITO.apply(Text.Color.VERDE.apply("[Success]")) +
                Text.Color.VERDE.apply(" O arquivo \"") +
                Text.Style.NEGRITO.apply(Text.Color.VERDE.apply(filename)) +
                Text.Color.VERDE.apply("\" foi copiado para \"") +
                Text.Style.NEGRITO.apply(Text.Color.VERDE.apply(path)) +
                Text.Color.VERDE.apply("\" com sucesso."));

        IOUtil.refreshMemory();
    }

    @Override
    public String help() {
        return "Uso: cp <filename> <path>\n"
                + "Exemplo: cp exemplo.txt documentos\n"
                + "Copia o arquivo especificado para o diretório desejado.";
    }

    private File findFile(String filename) {
        return FileSystemSimulator.CURRENT_DIRECTORY.getFiles().stream()
                .filter(file -> (file.name + "." + file.extension).equals(filename))
                .findFirst()
                .orElse(null);
    }

    private Directory findDirectory(String path) {
        return FileSystemSimulator.CURRENT_DIRECTORY.getDirectories().stream()
                .filter(dir -> dir.name.equalsIgnoreCase(path))
                .findFirst()
                .orElse(null);
    }

    private void copyFile(File file, Directory targetDirectory) {
        new File(file.name, file.extension, file.content, targetDirectory);
    }
}
