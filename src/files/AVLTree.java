package files;

class AVLTree<T extends Comparable<T>> {

    private class Node {
        T value;
        int height;
        Node left, right;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;

    // Retorna a altura de um nó
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Calcula o balanceamento de um nó
    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Rotação à direita
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Rotação à esquerda
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Inserir um valor na árvore
    public void insert(T value) {
        root = insert(root, value);
    }

    private Node insert(Node node, T value) {
        if (node == null) {
            return new Node(value);
        }

        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insert(node.right, value);
        } else {
            return node; // Não permite duplicatas
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // Caso 1 - Esquerda Esquerda
        if (balance > 1 && value.compareTo(node.left.value) < 0) {
            return rotateRight(node);
        }

        // Caso 2 - Direita Direita
        if (balance < -1 && value.compareTo(node.right.value) > 0) {
            return rotateLeft(node);
        }

        // Caso 3 - Esquerda Direita
        if (balance > 1 && value.compareTo(node.left.value) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Caso 4 - Direita Esquerda
        if (balance < -1 && value.compareTo(node.right.value) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Remover um valor da árvore
    public void remove(T value) {
        root = remove(root, value);
    }

    private Node remove(Node node, T value) {
        if (node == null) {
            return null;
        }

        if (value.compareTo(node.value) < 0) {
            node.left = remove(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = remove(node.right, value);
        } else {
            if ((node.left == null) || (node.right == null)) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    return null;
                } else {
                    node = temp;
                }
            } else {
                Node temp = getMinValueNode(node.right);
                node.value = temp.value;
                node.right = remove(node.right, temp.value);
            }
        }

        if (node == null) {
            return null;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Encontrar o nó com o menor valor (para remoção)
    private Node getMinValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Exibir a árvore em ordem
    public void displayInOrder() {
        displayInOrder(root);
    }

    private void displayInOrder(Node node) {
        if (node != null) {
            displayInOrder(node.left);
            System.out.print(node.value + " ");
            displayInOrder(node.right);
        }
    }
}
