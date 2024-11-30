package so.exceptions;

import so.enums.Text;

public class CommandNotFoundException extends SoException{
    public CommandNotFoundException(String input) {
        super(
                Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply("[Erro]")) + Text.Color.VERMELHO.apply(" O comando \"") + Text.Style.NEGRITO.apply(Text.Color.VERMELHO.apply(input)) + Text.Color.VERMELHO.apply("\" não foi identificado!")
        );
    }
}
