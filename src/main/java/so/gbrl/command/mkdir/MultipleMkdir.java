package so.gbrl.command.mkdir;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.enums.Text;
import so.gbrl.files.Directory;
import so.gbrl.utils.IOUtil;

import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class MultipleMkdir extends CommandBase {
    public MultipleMkdir() {
        super("^mkdir( -p)? (('[^']*'|[^'/ ]+)(/('[^']*'|[^'/ ]+))*)$");
    }

    @Override
    public void run(Matcher matcher) {
        boolean createParents = matcher.group(1) != null;
        String path = matcher.group(2).replace("'", "");

        String[] directories = path.split("/");

        Directory currentDirectory = FileSystemSimulator.CURRENT_DIRECTORY;

        for (String dirName : directories) {
            Directory existingDir = currentDirectory.getDirectories().stream()
                    .filter(dir -> dir.name.equals(dirName))
                    .findFirst()
                    .orElse(null);

            if (existingDir != null) {
                currentDirectory = existingDir;
            } else if (createParents) {
                currentDirectory = new Directory(dirName, currentDirectory);
            } else {
                println(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) +
                        Text.Color.VERMELHO.apply(" O diretório \"") +
                        Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(dirName)) +
                        Text.Color.VERMELHO.apply("\" não existe e o flag -p não foi usado."));
                return;
            }
        }

        println(Text.Style.NEGRITO.apply(Text.Color.VERDE.apply("[Success]")) +
                Text.Color.VERDE.apply(" O caminho \"") +
                Text.Style.NEGRITO.apply(Text.Color.VERDE.apply(path)) +
                Text.Color.VERDE.apply("\" foi criado com sucesso."));
        IOUtil.refreshMemory();
    }

    @Override
    public String help() {
        return "Uso: mkdir [-p] <caminho>\n"
                + "Exemplo: mkdir -p pasta1/pasta2/pasta3\n"
                + "Cria um ou mais diretórios. Use -p para criar subdiretórios automaticamente.";
    }
}
