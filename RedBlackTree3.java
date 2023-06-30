// Java program to implement insert operation in Red Black Tree.

public class RedBlackTree3 {

    private static Node root = null;
    String sColor = "";

    // utility function to rotate node anticlockwise.
    Node rotateLeft(Node myNode) {
        System.out.printf("left rotation!!\n");
        Node child = myNode.right;
        Node childLeft = child.left;

        child.left = myNode;
        myNode.right = childLeft;

        return child;
    }

    // utility function to rotate node clockwise.
    Node rotateRight(Node myNode) {
        System.out.printf("right rotation\n");
        Node child = myNode.left;
        Node childRight = child.right;

        child.right = myNode;
        myNode.left = childRight;

        return child;
    }

    // utility function to check whether
    // node is red in color or not.
    boolean isRed(Node myNode) {
        if (myNode == null)
            return false;
        return (myNode.color == true);
    }

    // utility function to swap color of two
    // nodes.
    void swapColors(Node node1, Node node2) {
        boolean temp = node1.color;
        node1.color = node2.color;
        node2.color = temp;
    }

    // insertion into Left Leaning Red Black Tree.
    Node insert(Node myNode, int data) {
        // Normal insertion code for any Binary
        // Search tree.
        if (myNode == null)
            return new Node(data);

        if (data < myNode.data)
            myNode.left = insert(myNode.left, data);

        else if (data > myNode.data)
            myNode.right = insert(myNode.right, data);

        else
            return myNode;

        // case 1.
        // when right child is Red but left child is
        // Black or doesn't exist.
        if (isRed(myNode.right) && !isRed(myNode.left)) {
            // left rotate the node to make it into
            // valid structure.
            myNode = rotateLeft(myNode);

            // swap the colors as the child node
            // should always be red
            swapColors(myNode, myNode.left);
        }

        // case 2
        // when left child as well as left grand child in Red
        if (isRed(myNode.left) && isRed(myNode.left.left)) {
            // right rotate the current node to make
            // it into a valid structure.
            myNode = rotateRight(myNode);
            swapColors(myNode, myNode.right);
        }

        // case 3
        // when both left and right child are Red in color.
        if (isRed(myNode.left) && isRed(myNode.right)) {
            // invert the color of node as well
            // it's left and right child.
            myNode.color = !myNode.color;

            // change the color to black.
            myNode.left.color = false;
            myNode.right.color = false;
        }
        return myNode;
    }

    // Inorder traversal
    void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
    }

    // --------- КЛАСС НОДЫ ------------------------------------
    class Node {
        Node left, right;
        int data;

        // red ==> true, black ==> false
        boolean color;

        Node(int data) {
            this.data = data;
            left = null;
            right = null;
            color = true; // New Node which is created is always red in color.
        }
    }

    // -------------------------------------------------------------

    public static void main(String[] args) {

        RedBlackTree3 node = new RedBlackTree3();
        System.out.println("Красно-черное дерево. Вариант 3");

        for (int i = 0; i < 10; i++) {
            root = node.insert(root, i);
            root.color = false;
        }

        // display the tree through inorder traversal.
        node.inorder(root);
    }
}