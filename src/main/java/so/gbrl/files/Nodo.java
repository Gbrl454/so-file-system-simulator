package so.gbrl.files;

public class Nodo {
    String nome;
    boolean isFolder;
    String conteudo;
    Nodo left;
    Nodo right;
    int height;
    AVLTree subTree;

    public Nodo(String nome, boolean isFolder) {
        this.nome = nome;
        this.isFolder = isFolder;
        this.conteudo = isFolder ? null : "";
        this.left = null;
        this.right = null;
        this.height = 1;
        this.subTree = isFolder ? new AVLTree() : null;
    }
}
