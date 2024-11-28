import files.Directory;
import files.File;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSystemSimulator {
    // private static final String SYS_REGEX = "\\n|\\s*&\\s*";
    // private static final String CD_REGEX =
    // "cd(\\s+\\.\\.|\\s+~?/\\S*|\\s+\\./\\S*|\\s+/\\S*|\\s*)";
    //
    //
    // public static void main(String[] args) {
    // Scanner sc = new Scanner(System.in);
    // while (true) {
    // for (String input : sc.nextLine().split(SYS_REGEX)) {
    //
    // Matcher matcherCd = Pattern.compile(CD_REGEX).matcher(input.trim());
    // if (matcherCd.matches()) {
    // System.out.println("CD");
    // } else {
    // System.out.println("Comando não reconhecido: " + input);
    // }
    // }
    // }
    // }

    private static final Directory ROOT = new Directory("ROOT", null);
    public static Directory currentDirectory = null;

    public static void main(String[] args) {
        currentDirectory = ROOT;

        String json = ROOT.toJson();

        Matcher matcherDirectory = Pattern.compile("^\\{\"name\":\"[^\"]+\",\"content\":\\[").matcher(json);
        if (matcherDirectory.find()) {
            String matchedPart = matcherDirectory.group();
            String remainingPart = json.substring(matchedPart.length(), json.length() - 2);

            System.out.println("Parte que casou com o regex:");
            System.out.println(matcherDirectory.group(1));

            System.out.println("Parte restante da string:");
            System.out.println(remainingPart);
        } else {
            System.out.println("A string não corresponde ao padrão.");
        }
    }
}
