public class LinkedListDouble {

    private Node head;
    private Node tail;
    private int size;

    // добавление элемента
    public void add(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
            size = 1;
            return;
        }

        tail.next = newNode;
        newNode.previous = tail;
        tail = newNode;
        size++;
    }

    /**
     * удаление элемента по значению
     * 
     * @param value - значение элемента
     * @return - true - если удаление выполнено
     */
    public boolean remove(int value) {
        if (remove(value, head) == null)
            return true;
        else
            return false;
    }

    //
    private Node remove(int value, Node startNode) {
        if (head == null)
            return null;
        Node currentNode = head;
        if (head.value == value) {
            head = head.next;
            size--;
            return head;
        }
        while (currentNode.next != null) {
            if (currentNode.next.value == value) {
                currentNode.next = currentNode.next.next;
                size--;
                return currentNode.next;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    /** удаление элемента по индексу
     * @param index - индекс элемента
     * @return - возвращается True - если удаление прошло успешно, иначе False
     */
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

    public int removeAll(int value) {
        if (head == null)
            return 0;
        int prevSize = this.size;
        Node currentNode = remove(value, head);
        while (currentNode != null)
            currentNode = remove(value, currentNode);
        return prevSize - size;
    }

    /** метод быстрой сортировки списка
     */
    public void sort() {
        sort(0, size - 1);
    }

    /** внутренний (приватный) метод быстрой сортировки списка
     * @param leftBorder - левая граница списка для сортировки
     * @param rightBorder - правая граница списка для сортировки
     */
    private void sort(int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        int pivot = this.getValue((leftMarker + rightMarker) / 2);
        do {
            while (this.getValue(leftMarker) < pivot)
                leftMarker++;
            while (this.getValue(rightMarker) > pivot)
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

    /** метод поиска индекса элемента списка по значению
     * @param value - искомое значение элемента списка
     * @return
     */
    public int findIndex(int value) {
        if (head == null)
            return -1;
        Node currentNode = head;
        for (int i = 0; currentNode != null; i++, currentNode = currentNode.next)
            if (currentNode.value == value)
                return i;
        return -1;
    }

    /** метод поиска элемента списка по значению
     * @param value - искомое значение элемента списка
     * @return
     */
    public Node find(int value) {
        Node currentNode = head;
        if (head == null)
            return null;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            if (currentNode.value == value)
                return currentNode;
        }
        ;
        return null;
    }

    /** метод получения элемента списка
    * @param index - индекс элемента
    */
    private Node getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node currentNode = head;
        for (int i = 0; i < index; i++)
            currentNode = currentNode.next;
        return currentNode;
    }

    /** метод получения значения элемента списка по индексу
     */
    private int getValue(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node currentNode = head;
        for (int i = 0; i < index; i++)
            currentNode = currentNode.next;
        return currentNode.value;
    }

    /** метод получения размера списка
     * @return
     */
    public int getSize() {
        return size;
    }

    /** метод перестановки значения в списке по индексам
     * @param index1 - индекс первого элемента
     * @param index2 - индекс второго элемента
     */
    private void swap(int index1, int index2) {
        if (index1 < 0 || index1 >= size)
            return;
        if (index2 < 0 || index2 >= size)
            return;
        Node node1 = this.getNode(index1);
        Node node2 = this.getNode(index2);
        int temp = node1.value;
        node1.value = node2.value;
        node2.value = temp;
    }

    /** Метод печати (вывода на экран) связного списка
     */
    public void print() {
        Node currentNode = head;
        System.out.print("[ ");
        while (currentNode != null) {
            System.out.print(currentNode.value + " ");
            currentNode = currentNode.next;
        }
        System.out.println("]");
    }

    /** метод разворота двухсвязного списка
     */
    public void revert() {
        Node leftNode = head;
        Node rightNode = tail;

        for (int i = 0; i < size/2; i++) {
            int temp = leftNode.value;
            leftNode.value = rightNode.value;
            rightNode.value = temp;
            leftNode = leftNode.next;
            rightNode = rightNode.previous;
        }
    }

    private class Node {
        int value;
        Node next;
        Node previous;

        Node() {
            next = null;
            previous = null;
        }

        Node(int _value) {
            this.value = _value;
        }

        Node(int _value, Node _next, Node _previous) {
            this.value = _value;
            this.next = _next;
            this.previous = _previous;
        }
    }
}