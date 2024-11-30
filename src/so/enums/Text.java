package so.enums;


public class Text {
    public enum Color implements AbstractColor {
        PRETO("\u001B[30m"), //
        VERMELHO("\u001B[31m"), //
        VERDE("\u001B[32m"), //
        AMARELO("\u001B[33m"), //
        AZUL("\u001B[34m"), //
        MAGENTA("\u001B[35m"), //
        CIANO("\u001B[36m"), //
        BRANCO("\u001B[37m"), //
        DEFAULT("\u001B[39m"); //

        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String apply(String text) {
            return code + text + DEFAULT.code;
        }
    }

    public enum Background implements AbstractColor {
        PRETO("\u001B[40m"), //
        VERMELHO("\u001B[41m"), //
        VERDE("\u001B[42m"), //
        AMARELO("\u001B[43m"), //
        AZUL("\u001B[44m"), //
        MAGENTA("\u001B[45m"), //
        CIANO("\u001B[46m"), //
        BRANCO("\u001B[47m"), //
        DEFAULT("\u001B[49m"); //

        private final String code;

        Background(String code) {
            this.code = code;
        }

        @Override
        public String apply(String text) {
            return code + text + DEFAULT.code;
        }
    }

    public enum Style implements AbstractColor {
        NEGRITO("\u001B[1m"), //
        FINO("\u001B[2m"), //
        ITALICO("\u001B[3m"), //
        SUBLINHADO("\u001B[4m"), //
        BLINK("\u001B[5m"), //
        SWAP("\u001B[7m"), //
        DEFAULT("\u001B[0m"); //
        private final String code;

        Style(String code) {
            this.code = code;
        }

        @Override
        public String apply(String text) {
            return code + text + DEFAULT.code;
        }
    }

    private interface AbstractColor {
        String apply(String text);
    }
}

