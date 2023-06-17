import java.util.Random;

public class DZ_3 {

    public static void main(String[] args) {

        // ОДНОСВЯЗНЫЙ СПИСОК
        System.out.println();
        System.out.println("Односвзяный список:");
        LinkedList list = new LinkedList();
        for (int i = 0; i < 20; i++) {
            list.add(new Random().nextInt(20));
        }

        list.print();
        list.revert(); // разворот односвязного списка
        list.print();

        // ДВУХСВЯЗНЫЙ СПИСОК
        System.out.println();
        System.out.println("Двухсвязный список:");
        LinkedListDouble listDouble = new LinkedListDouble();
        for (int i = 0; i < 20; i++) {
            listDouble.add(new Random().nextInt(20));
        }

        listDouble.print();
        listDouble.revert(); // разворот двухсвязного списка
        listDouble.print();
        
        // listDouble.sort(); // сортировка двухсвязного списка
        // listDouble.print();
    }
}