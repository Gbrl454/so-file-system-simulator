import files.Directory;
import files.File;

import java.util.List;

public class FileSystemSimulator {
//    private static final String SYS_REGEX = "\\n|\\s*&\\s*";
//    private static final String CD_REGEX = "cd(\\s+\\.\\.|\\s+~?/\\S*|\\s+\\./\\S*|\\s+/\\S*|\\s*)";
//
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            for (String input : sc.nextLine().split(SYS_REGEX)) {
//
//                Matcher matcherCd = Pattern.compile(CD_REGEX).matcher(input.trim());
//                if (matcherCd.matches()) {
//                    System.out.println("TODO CD");
//                } else {
//                    System.out.println("Comando não reconhecido: " + input);
//                }
//            }
//        }
//    }

    private static final Directory ROOT = new Directory("ROOT", null);
    public static Directory currentDirectory = null;


    public static void main(String[] args) {
        currentDirectory = ROOT;

        new File("File 1", "txt", List.of("teste", "testando", "testado"), ROOT);
        new File("File 2", "txt", List.of("teste", "testando", "testado"), ROOT);
        new File("File 3", "txt", List.of("teste", "testando", "testado"), ROOT);

        Directory dir1 = new Directory("Directory 1", ROOT);
        new File("File 11", "txt", List.of("teste", "testando", "testado"), dir1);
        new File("File 12", "txt", List.of("teste", "testando", "testado"), dir1);
        new File("File 13", "txt", List.of("teste", "testando", "testado"), dir1);

        Directory dir2 = new Directory("Directory 2", ROOT);
        new File("File 21", "txt", List.of("teste", "testando", "testado"), dir2);
        new File("File 22", "txt", List.of("teste", "testando", "testado"), dir2);
        new File("File 23", "txt", List.of("teste", "testando", "testado"), dir2);

        Directory dir3 = new Directory("Directory 3", ROOT);
        new File("File 31", "txt", List.of("teste", "testando", "testado"), dir3);
        new File("File 32", "txt", List.of("teste", "testando", "testado"), dir3);
        new File("File 33", "txt", List.of("teste", "testando", "testado"), dir3);

        System.out.println(ROOT.toJson());
    }
}
