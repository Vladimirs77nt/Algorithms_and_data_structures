public class LinkedListT<T extends Comparable<T>> {
    private Node head;
    private int size;

    public void add(T value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            size = 1;
            return;
        }
        Node currentNode = head;
        while (currentNode.next != null)
            currentNode = currentNode.next;
        currentNode.next = newNode;
        size++;
    }

    public boolean remove(T value) {
        if (remove(value, head) == null)
            return true;
        else
            return false;
    }

    private Node remove(T value, Node startNode) {
        if (head == null)
            return null;
        Node currentNode = head;
        if (head.value.compareTo(value) == 0) {
            head = head.next;
            size--;
            return head;
        }
        while (currentNode.next != null) {
            if (currentNode.next.value.compareTo(value) == 0) {
                currentNode.next = currentNode.next.next;
                size--;
                return currentNode.next;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    public boolean removeAt(int index) {
        if (head == null || index >= size)
            return false;
        if (index == 0) {
            head = head.next;
            size--;
            return true;
        }
        Node currentNode = this.getNode(index - 1);
        currentNode.next = currentNode.next.next;
        size--;
        return true;
    }

    public int removeAll(T value) {
        if (head == null)
            return 0;
        int prevSize = this.size;
        Node currentNode = remove(value, head);
        while (currentNode != null)
            currentNode = remove(value, currentNode);
        return prevSize - size;
    }

    public void sort() {
        sort(0, size - 1);
    }

    private void sort(int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        T pivot = this.getValue((leftMarker + rightMarker) / 2);
        do {
            while (this.getValue(leftMarker).compareTo(pivot) < 0)
                leftMarker++;
            while (this.getValue(rightMarker).compareTo(pivot) > 0)
                rightMarker--;
            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker)
                    swap(leftMarker, rightMarker);
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);
        if (leftMarker < rightBorder)
            sort(leftMarker, rightBorder);
        if (leftBorder < rightMarker)
            sort(leftBorder, rightMarker);
    }

    public int find(T value) {
        if (head == null)
            return -1;
        Node currentNode = head;
        for (int i = 0; currentNode != null; i++, currentNode = currentNode.next)
            if (currentNode.value.compareTo(value) == 0)
                return i;
        return -1;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node currentNode = head;
        for (int i = 0; i < index; i++)
            currentNode = currentNode.next;
        return currentNode;
    }

    private T getValue(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node currentNode = head;
        for (int i = 0; i < index; i++)
            currentNode = currentNode.next;
        return currentNode.value;
    }

    public int getSize() {
        return size;
    }

    private void swap(int index1, int index2) {
        if (index1 < 0 || index1 >= size)
            return;
        if (index2 < 0 || index2 >= size)
            return;
        Node node1 = this.getNode(index1);
        Node node2 = this.getNode(index2);
        T temp = node1.value;
        node1.value = node2.value;
        node2.value = temp;
    }

    public void print() {
        Node currentNode = head;
        System.out.print("[ ");
        while (currentNode != null) {
            System.out.print(currentNode.value + " ");
            currentNode = currentNode.next;
        }
        System.out.println("]");
    }

    private class Node {
        T value;
        Node next;

        Node() {
            next = null;
        }

        Node(T _value) {
            this.value = _value;
        }

        Node(T _value, Node _next) {
            this.value = _value;
            this.next = _next;
        }
    }
}
