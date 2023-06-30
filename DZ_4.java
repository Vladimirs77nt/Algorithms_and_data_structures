import java.util.Random;

public class DZ_4 {
    public static void main(String[] args) {

        System.out.println();
        System.out.println("Левосторонее красно-черное бинарное дерево");
        BinTree<Integer> tree = new BinTree<Integer>();

        // заполнение дерева значениями от 0 до 19
        for (int i = 0; i < 20; i++) {
            // int value = new Random().nextInt(20);
            System.out.println("Итерация №" + i + "  добавленное значение: " + i);
            tree.add(i);
            System.out.println();
        }

        System.out.println("Результат:");
        tree.print();
    }
}