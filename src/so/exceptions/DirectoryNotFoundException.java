package so.exceptions;

import so.enums.Text;

public class DirectoryNotFoundException extends SoException {
    public DirectoryNotFoundException(String directoryName) {
        super(Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) + Text.Color.VERMELHO.apply(" O diretório \"") + Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(directoryName)) + Text.Color.VERMELHO.apply("\" não foi encontrada no diretório atual!"));
    }
}
