package so.gbrl.exceptions;

import so.gbrl.FileSystemSimulator;
import so.gbrl.enums.Text;

public class FileNotFoundException extends SoException {
    public FileNotFoundException(String fileNameWithExtension) {
        super(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) + //
                Text.Color.VERMELHO.apply(" O arquivo ") + //
                Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(fileNameWithExtension)) + //
                Text.Color.VERMELHO.apply(" não foi encontrada no diretório ") + //
                Text.Color.VERMELHO.apply(Text.Style.NEGRITO.apply(FileSystemSimulator.CURRENT_DIRECTORY.name)) + //
                Text.Color.VERMELHO.apply("!"));
    }
}
