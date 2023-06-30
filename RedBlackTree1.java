public class RedBlackTree1 {
    RBTreeNode prime;
    private final boolean RED = false; // нода КРАСНАЯ если значение color = false
    private final boolean BLACK = true; // нода ЧЕРНАЯ если значение color = true

    // класс ноды
    private class RBTreeNode {
        private final boolean RED = false;
        private int value;         // значение ноды
        private boolean color;     // цвет ноды
        private RBTreeNode left;   // левая нода
        private RBTreeNode right;  // правая нода
        private RBTreeNode parent;  // нода-родитель

        // инициализация новой ноды со значением value
        public RBTreeNode(int value) {
            this.value = value;
            this.color = RED; // новая нода всегда красная
        }

//---------- ГЕТТЕРЫ И СЕТТЕРЫ ----------------        
        public int getvalue() {
            return value;
        }

        // сеттер значения value
        public void setvalue(int value) {
            this.value = value;
        }

        // геттер цвета
        public boolean getColor() {
            return color;
        }

        // сеттер цвета
        public void setColor(boolean color) {
            this.color = color;
        }

        public RBTreeNode getLeft() {
            return left;
        }

        public void setLeft(RBTreeNode left) {
            this.left = left;
        }

        public RBTreeNode getRight() {
            return right;
        }

        public void setRight(RBTreeNode right) {
            this.right = right;
        }

        public RBTreeNode getParent() {
            return parent;
        }

        public void setParent(RBTreeNode parent) {
            this.parent = parent;
        }

//---------- СТРОКОВОЕ ПРЕДСТАВЛЕНИЕ ЭЛЕМЕНТА ДЕВЕРА ----------------        
        @Override
        public String toString() {
            return "RBTreeNode{" +
                    ",value=" + value +
                    ", color=" + color +
                    '}';
        }
    }

    /**
     * Поиск Ноды со значением value
     * @param value
     * @return - возвращает Ноду или Null если такого значения нет
     */
    public RBTreeNode find(int value) {
        RBTreeNode tmp = prime;
        while (tmp != null) {
            if (tmp.getvalue() == value)
                return tmp;
            else if (tmp.getvalue() > value)
                tmp = tmp.getLeft();
            else
                tmp = tmp.getRight();
        }
        return null;
    }

    /**
     * Добавление нового элмента дерева (Ноды)
     * @param value
     */
    public void insert(int value) {
        RBTreeNode node = new RBTreeNode(value);
        if (prime == null) {
            prime = node;
            node.setColor(BLACK);
            return;
        }
        RBTreeNode parent = prime;
        RBTreeNode children = null;
        if (value <= parent.getvalue()) {
            children = parent.getLeft();
        } else {
            children = parent.getRight();
        }
        // поиск позиции
        while (children != null) {
            parent = children;
            if (value <= parent.getvalue()) {
                children = parent.getLeft();
            } else {
                children = parent.getRight();
            }
        }
        if (value <= parent.getvalue()) {
            parent.setLeft(node);
        } else {
            parent.setRight(node);
        }
        node.setParent(parent);

        // fix up
        insertFix(node);
    }

    private void insertFix(RBTreeNode node) {
        RBTreeNode father, grandFather;
        while ((father = node.getParent()) != null && father.getColor() == RED) {
            grandFather = father.getParent();
            if (grandFather.getLeft() == father) { // F - ситуация с левым сыном G, как и в предыдущем анализе
                RBTreeNode uncle = grandFather.getRight();
                if (uncle != null && uncle.getColor() == RED) {
                    setBlack(father);
                    setBlack(uncle);
                    setRed(grandFather);
                    node = grandFather;
                    continue;
                }
                if (node == father.getRight()) {
                    leftRotate(father);
                    RBTreeNode tmp = node;
                    node = father;
                    father = tmp;
                }
                setBlack(father);
                setRed(grandFather);
                rightRotate(grandFather);
            } else { // F - случай правого сына G, симметричная операция
                RBTreeNode uncle = grandFather.getLeft();
                if (uncle != null && uncle.getColor() == RED) {
                    setBlack(father);
                    setBlack(uncle);
                    setRed(grandFather);
                    node = grandFather;
                    continue;
                }
                if (node == father.getLeft()) {
                    rightRotate(father);
                    RBTreeNode tmp = node;
                    node = father;
                    father = tmp;
                }
                setBlack(father);
                setRed(grandFather);
                leftRotate(grandFather);
            }
        }
        setBlack(prime);
    }

    public void delete(int value) {
        delete(find(value));
    }

    private void delete(RBTreeNode node) {
        if (node == null)
            return;
        if (node.getLeft() != null && node.getRight() != null) {
            RBTreeNode replaceNode = node;
            RBTreeNode tmp = node.getRight();
            while (tmp != null) {
                replaceNode = tmp;
                tmp = tmp.getLeft();
            }
            int t = replaceNode.getvalue();
            replaceNode.setvalue(node.getvalue());
            node.setvalue(t);
            delete(replaceNode);
            return;
        }
        RBTreeNode replaceNode = null;
        if (node.getLeft() != null)
            replaceNode = node.getLeft();
        else
            replaceNode = node.getRight();

        RBTreeNode parent = node.getParent();
        if (parent == null) {
            prime = replaceNode;
            if (replaceNode != null)
                replaceNode.setParent(null);
        } else {
            if (replaceNode != null)
                replaceNode.setParent(parent);
            if (parent.getLeft() == node)
                parent.setLeft(replaceNode);
            else {
                parent.setRight(replaceNode);
            }
        }
        if (node.getColor() == BLACK)
            removeFix(parent, replaceNode);

    }

    // Дополнительный цвет находится в узле
    private void removeFix(RBTreeNode father, RBTreeNode node) {
        while ((node == null || node.getColor() == BLACK) && node != prime) {
            if (father.getLeft() == node) { // S - левый сын P, как и в предыдущем анализе
                RBTreeNode brother = father.getRight();
                if (brother != null && brother.getColor() == RED) {
                    setRed(father);
                    setBlack(brother);
                    leftRotate(father);
                    brother = father.getRight();
                }
                if (brother == null || (isBlack(brother.getLeft()) && isBlack(brother.getRight()))) {
                    setRed(brother);
                    node = father;
                    father = node.getParent();
                    continue;
                }
                if (isRed(brother.getLeft())) {
                    setBlack(brother.getLeft());
                    setRed(brother);
                    rightRotate(brother);
                    brother = brother.getParent();
                }

                brother.setColor(father.getColor());
                setBlack(father);
                setBlack(brother.getRight());
                leftRotate(father);
                node = prime;// вне цикла
            } else { // S - случай правого сына P, симметричная операция
                RBTreeNode brother = father.getLeft();
                if (brother != null && brother.getColor() == RED) {
                    setRed(father);
                    setBlack(brother);
                    rightRotate(father);
                    brother = father.getLeft();
                }
                if (brother == null || (isBlack(brother.getLeft()) && isBlack(brother.getRight()))) {
                    setRed(brother);
                    node = father;
                    father = node.getParent();
                    continue;
                }
                if (isRed(brother.getRight())) {
                    setBlack(brother.getRight());
                    setRed(brother);
                    leftRotate(brother);
                    brother = brother.getParent();
                }

                brother.setColor(father.getColor());
                setBlack(father);
                setBlack(brother.getLeft());
                rightRotate(father);
                node = prime;// вне цикла
            }
        }

        if (node != null)
            node.setColor(BLACK);
    }

    private boolean isBlack(RBTreeNode node) {
        if (node == null)
            return true;
        return node.getColor() == BLACK;
    }

    private boolean isRed(RBTreeNode node) {
        if (node == null)
            return false;
        return node.getColor() == RED;
    }

    private void leftRotate(RBTreeNode node) {
        RBTreeNode right = node.getRight();
        RBTreeNode parent = node.getParent();
        if (parent == null) {
            prime = right;
            right.setParent(null);
        } else {
            if (parent.getLeft() != null && parent.getLeft() == node) {
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
            right.setParent(parent);
        }
        node.setParent(right);
        node.setRight(right.getLeft());
        if (right.getLeft() != null) {
            right.getLeft().setParent(node);
        }
        right.setLeft(node);
    }

    private void rightRotate(RBTreeNode node) {
        RBTreeNode left = node.getLeft();
        RBTreeNode parent = node.getParent();
        if (parent == null) {
            prime = left;
            left.setParent(null);
        } else {
            if (parent.getLeft() != null && parent.getLeft() == node) {
                parent.setLeft(left);
            } else {
                parent.setRight(left);
            }
            left.setParent(parent);
        }
        node.setParent(left);
        node.setLeft(left.getRight());
        if (left.getRight() != null) {
            left.getRight().setParent(node);
        }
        left.setRight(node);
    }

    private void setBlack(RBTreeNode node) {
        node.setColor(BLACK);
    }

    private void setRed(RBTreeNode node) {
        node.setColor(RED);
    }

    public void printTree() {
        printTree(prime);
    }

    private void printTree(RBTreeNode node) {
        if (node == null)
            return;
        printTree(node.getLeft());
        System.out.println(node);
        printTree(node.getRight());
    }

    public static void main(String[] args) {
        RedBlackTree1 tree = new RedBlackTree1();
        for (int i = 0; i < 20; i++) {
            tree.insert(i);
        }
        tree.printTree();
    }
}