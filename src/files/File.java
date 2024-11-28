package files;

public class File {
    private String name; // Nome do arquivo
    private String content; // Conteúdo do arquivo
    private long size; // Tamanho do arquivo em bytes

    // Construtor
    public File(String name) {
        this.name = name;
        this.content = "";
        this.size = 0;
    }

    // Retorna o nome do arquivo
    public String getName() {
        return name;
    }

    // Define o nome do arquivo
    public void setName(String name) {
        this.name = name;
    }

    // Retorna o conteúdo do arquivo
    public String getContent() {
        return content;
    }

    // Define o conteúdo do arquivo e atualiza o tamanho
    public void setContent(String content) {
        this.content = content;
        this.size = content.getBytes().length; // Atualiza o tamanho em bytes
    }

    // Retorna o tamanho do arquivo em bytes
    public long getSize() {
        return size;
    }

    // Representação do arquivo em formato de string
    @Override
    public String toString() {
        return "File{name='" + name + "', size=" + size + " bytes}";
    }
}
