package so.gbrl.files;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AVLTree<T extends Comparable<T>> implements Iterable<T> {

    private static class AVLNode<T> {
        T data;
        AVLNode<T> left, right;
        int height;

        AVLNode(T data) {
            this.data = data;
            this.height = 1;
        }
    }

    private AVLNode<T> root;
    private int size;

    private int height(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(AVLNode<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private AVLNode<T> rotateRight(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private AVLNode<T> rotateLeft(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(T data) {
        root = insert(root, data);
        size++;
    }

    private AVLNode<T> insert(AVLNode<T> node, T data) {
        if (node == null) return new AVLNode<>(data);

        if (data.compareTo(node.data) < 0) {
            node.left = insert(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insert(node.right, data);
        } else {
            size--;
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rotateRight(node);
        }
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return rotateLeft(node);
        }
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public boolean contains(T data) {
        return contains(root, data);
    }

    private boolean contains(AVLNode<T> node, T data) {
        if (node == null) return false;
        if (data.equals(node.data)) return true;
        if (data.compareTo(node.data) < 0) return contains(node.left, data);
        return contains(node.right, data);
    }

    private void inOrderTraversal(AVLNode<T> node, List<T> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.data);
            inOrderTraversal(node.right, result);
        }
    }

    public List<T> toList() {
        List<T> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return toList().iterator();
    }
}
