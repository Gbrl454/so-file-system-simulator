package so.gbrl.command.create;

import so.gbrl.FileSystemSimulator;
import so.gbrl.command.CommandBase;
import so.gbrl.enums.Text;
import so.gbrl.files.File;

import java.util.List;
import java.util.regex.Matcher;

import static so.gbrl.FileSystemSimulator.println;

public class CreateFile extends CommandBase {

    public CreateFile() {
        super("^create\\s+(\\w+(\\.\\w+)?)$");
    }

    @Override
    public void run(Matcher matcher) {
        if (matcher.matches()) {
            String fullFileName = matcher.group(1);
            String[] parts = fullFileName.split("\\.");

            String name = parts[0];
            String extension = parts.length > 1 ? parts[1] : "";

            boolean fileExists = FileSystemSimulator.CURRENT_DIRECTORY.listContent().stream()
                    .filter(it -> it instanceof File)
                    .map(it -> (File) it)
                    .anyMatch(file -> file.name.equals(name) && file.extension.equals(extension));

            if (fileExists) {
                println(Text.Color.VERMELHO.apply("Erro: O arquivo '" + fullFileName + "' já existe."));
                return;
            }

            try {
                new File(name, extension, List.of(), FileSystemSimulator.CURRENT_DIRECTORY);
                println(Text.Color.VERDE.apply("Arquivo '" + fullFileName + "' criado com sucesso."));
            } catch (RuntimeException e) {
                println(Text.Color.VERMELHO.apply("Erro ao criar o arquivo: " + e.getMessage()));
            }
        } else {
            println(Text.Color.VERMELHO.apply("Erro: Nome do arquivo inválido."));
        }
    }


    @Override
    public String help() {
        return "Uso: create <filename>\n"
                + "Exemplo: create arquivo.txt\n"
                + "Cria um novo arquivo no diretório atual.";
    }
}
