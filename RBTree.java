// КРАСНО-ЧЕРНОЕ ДЕРЕВО с семинара

import java.util.ArrayList;
import java.util.List;

public class RBTree<T extends Comparable<T>> {

    Node prime; // корень дерева

    /**
     * класс ноды:
     * value - значение ноды,
     * color - цвет ноды,
     * left - левая нода,
     * right - правая нода
     */
    private class Node {
        T value;
        Color color;
        Node left;
        Node right;

        Node(T _value) {
            this.value = _value;
            left = null;
            right = null;
            color = Color.Red;
        }
    }

    enum Color {
        Red, Black
    }

    private class PrintNode {
        Node node;
        String str;
        int depth;

        public PrintNode() {
            node = null;
            str = " ";
            depth = 0;
        }

        public PrintNode(Node node) {
            depth = 0;
            this.node = node;
            this.str = node.value.toString();
        }
    }

    /**
     * метод добавления элемента (ноды) в дерево
     * 
     * @param value - значение элемента
     * @return - возвращает True/False (добавлено/нет)
     */
    public boolean add(T value) {
        if (prime == null) {
            Node newNode = new Node(value);
            prime = newNode;
            prime.color = Color.Black;
            return true;
        }
        if (addX(prime, value) != null) {
            prime = equalization(prime);
            return true;
        }
        return false;
    }

    /**
     * внутренний рекурсивный метод добавления элемента
     * 
     * @param node  - текущая рассматриваемая нода (текущий корень)
     * @param value - добавляемое значение
     * @return - возвращает созданную ноду
     */
    private Node addX(Node node, T value) {
        if (node.value.compareTo(value) == 0)
            return null; // отчечение одинаковых значений

        // если добавляемое значение меньше текущей ноды...
        if (node.value.compareTo(value) > 0) {
            if (node.left == null) { // ...и левый потомок пустой, то добавляем ее к левому потомку
                node.left = new Node(value);
                return node.left;
            }
            Node newNode = addX(node.left, value); // ...левый потомок есть, то добавляем элемент по отношению левого
                                                   // потомка ноды
            node.left = equalization(node.left);
            return newNode;
        }
        // если добавляемое значение больше текущей ноды...
        else {
            if (node.right == null) { // ...и правый потомок пустой, то добавляем ее к правому потомку
                node.right = new Node(value);
                return node.right;
            }
            Node newNode = addX(node.right, value); // ...правый потомок есть, то добавляем элемент по отношению правого
                                                    // потомка ноды
            node.right = equalization(node.right);
            return newNode;
        }
    }

    /**
     * метод балансировки / выравнивания дерева
     * 
     * @param node
     * @return
     */
    private Node equalization(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.right != null && result.right.color == Color.Red
                    && (result.left == null || result.left.color == Color.Black)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.left != null && result.left.color == Color.Red
                    && result.left.left != null && result.left.left.color == Color.Red) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.left != null && result.left.color == Color.Red
                    && result.right != null && result.right.color == Color.Red) {
                needRebalance = true;
                colorSwap(result);
            }
        } while (needRebalance);
        return result;
    }

    /**
     * замена цветов у двух нод
     * 
     * @param node
     */
    private void colorSwap(Node node) {
        node.right.color = Color.Black;
        node.left.color = Color.Black;
        node.color = Color.Red;
    }

    /**
     * левый разворот
     * 
     * @param node
     * @return
     */
    private Node leftSwap(Node node) {
        Node left = node.left;
        Node between = left.right;
        left.right = node;
        node.left = between;
        left.color = node.color;
        node.color = Color.Red;
        return left;
    }

    /**
     * правый разворот
     * 
     * @param node
     * @return
     */
    private Node rightSwap(Node node) {
        Node right = node.right;
        Node between = right.left;
        right.left = node;
        node.right = between;
        right.color = node.color;
        node.color = Color.Red;
        return right;
    }

    // -------------------------------
    /**
     * Метод печати дерева
     */
    public void print() {
        int maxDepth = maxDepth() + 3;
        int nodeCount = nodeCount(prime, 0);
        int width = 50;// maxDepth * 4 + 2;
        int height = nodeCount * 5;
        List<List<PrintNode>> list = new ArrayList<List<PrintNode>>();
        for (int i = 0; i < height; i++) /* Создание ячеек массива */ {
            ArrayList<PrintNode> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new PrintNode());
            }
            list.add(row);
        }

        list.get(height / 2).set(0, new PrintNode(prime));
        list.get(height / 2).get(0).depth = 0;

        for (int j = 0; j < width; j++) /* Принцип заполнения */ {
            for (int i = 0; i < height; i++) {
                PrintNode currentNode = list.get(i).get(j);
                if (currentNode.node != null) {
                    currentNode.str = currentNode.node.value.toString();
                    if (currentNode.node.left != null) {
                        int in = i + (maxDepth / (int) Math.pow(2, currentNode.depth));
                        int jn = j + 3;
                        printLines(list, i, j, in, jn);
                        list.get(in).get(jn).node = currentNode.node.left;
                        list.get(in).get(jn).depth = list.get(i).get(j).depth + 1;

                    }
                    if (currentNode.node.right != null) {
                        int in = i - (maxDepth / (int) Math.pow(2, currentNode.depth));
                        int jn = j + 3;
                        printLines(list, i, j, in, jn);
                        list.get(in).get(jn).node = currentNode.node.right;
                        list.get(in).get(jn).depth = list.get(i).get(j).depth + 1;
                    }

                }
            }
        }
        for (int i = 0; i < height; i++) /* Чистка пустых строк */ {
            boolean flag = true;
            for (int j = 0; j < width; j++) {
                if (list.get(i).get(j).str != " ") {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                list.remove(i);
                i--;
                height--;
            }
        }

        for (var row : list) {
            for (var item : row) {
                System.out.print(item.str + " ");
            }
            System.out.println();
        }
    }

    private void printLines(List<List<PrintNode>> list, int i, int j, int i2, int j2) {
        if (i2 > i) // Идём вниз
        {
            while (i < i2) {
                i++;
                list.get(i).get(j).str = "|";
            }
            list.get(i).get(j).str = "\\";
            while (j < j2) {
                j++;
                list.get(i).get(j).str = "-";
            }
        } else {
            while (i > i2) {
                i--;
                list.get(i).get(j).str = "|";
            }
            list.get(i).get(j).str = "/";
            while (j < j2) {
                j++;
                list.get(i).get(j).str = "-";
            }
        }
    }

    public int maxDepth() {
        return maxDepth2(0, prime);
    }

    private int maxDepth2(int depth, Node node) {
        depth++;
        int left = depth;
        int right = depth;
        if (node.left != null)
            left = maxDepth2(depth, node.left);
        if (node.right != null)
            right = maxDepth2(depth, node.right);
        return left > right ? left : right;
    }

    private int nodeCount(Node node, int count) {
        if (node != null) {
            count++;
            return count + nodeCount(node.left, 0) + nodeCount(node.right, 0);
        }
        return count;
    }
}