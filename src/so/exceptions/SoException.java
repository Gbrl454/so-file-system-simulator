package so.exceptions;

import so.enums.Text;

public class SoException extends RuntimeException {
    public static final String UNEXPECTED_ERROR = Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) + Text.Color.VERMELHO.apply(" Ocorreu um problema inesperado!");

    public SoException(String message) {
        super(message);
    }

    public SoException() {
        super(UNEXPECTED_ERROR);
    }
}



