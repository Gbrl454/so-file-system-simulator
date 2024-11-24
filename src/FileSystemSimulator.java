import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSystemSimulator {
    private static final String SYS_REGEX = "\\n|\\s*&\\s*";
    private static final String CD_REGEX = "cd(\\s+\\.\\.|\\s+~?/\\S*|\\s+\\./\\S*|\\s+/\\S*|\\s*)";


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            for (String input : sc.nextLine().split(SYS_REGEX)) {

                Matcher matcherCd = Pattern.compile(CD_REGEX).matcher(input.trim());
                if (matcherCd.matches()) {
                    System.out.println("TODO CD");
                } else {
                    System.out.println("Comando não reconhecido: " + input);
                }
            }
        }
    }
}
