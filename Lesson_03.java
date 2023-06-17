import java.util.Random;

public class Lesson_03 {

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.add(9);
        list.add(5);
        list.add(1);
        list.add(3);
        list.add(9);
        list.add(4);
        list.add(9);

        list.print();

        list.removeAll(9);

        list.print();

        for (int i = 0; i < 10; i++) {
            list.add(new Random().nextInt(20));
        }

        list.print();
    }
}