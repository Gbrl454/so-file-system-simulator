package so.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Journal {

    private final List<String> log;

    public Journal() {
        this.log = new ArrayList<>();
    }

    public void log(String mensagem) {
        String registro = new Date() + " - " + mensagem;
        log.add(registro);
        System.out.println(registro);
    }

    public void salvarLog(String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (String registro : log) {
                writer.write(registro);
                writer.newLine();
            }
            System.out.println("Log salvo em " + caminho);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o log: " + e.getMessage());
        }
    }
}