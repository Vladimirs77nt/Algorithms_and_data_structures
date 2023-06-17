import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;

public class Lesson_02 {
    public static void main(String[] args) {

        int[] array = new int[50000000];
        for (int i = 0; i < array.length; i++)
            array[i] = new Random().nextInt(200);
        int[] array2 = array;
        LocalTime timeQuickSortStart = LocalTime.now();
        quickSort(array);
        LocalTime timeQuickSortEnd = LocalTime.now();

        LocalTime timeInsertSortStart = LocalTime.now();
        inserterSort(array2);
        LocalTime timeInsertSortEnd = LocalTime.now();

        System.out.println("Быстрая сортировка: " + Duration.between(timeQuickSortStart, timeQuickSortEnd));

        System.out.println("Сортировка вставками: " + Duration.between(timeInsertSortStart, timeInsertSortEnd));
    }


    public static void quickSort(int[] array) {
        quickSortRecursive(array, 0, array.length - 1);
    }

    public static void quickSortRecursive(int[] array, int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        int pivot = array[(leftMarker + rightMarker) / 2];
        do {
            while (array[leftMarker] < pivot)
                leftMarker++;
            while (array[rightMarker] > pivot)
                rightMarker--;
            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    int temp = array[leftMarker];
                    array[leftMarker] = array[rightMarker];
                    array[rightMarker] = temp;
                }
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);
        if (leftMarker < rightBorder)
            quickSortRecursive(array, leftMarker, rightBorder);
        if (leftBorder < rightMarker)
            quickSortRecursive(array, leftBorder, rightMarker);
    }

    public static void inserterSort(int[] array) {
        for (int left = 0; left < array.length; left++) {
            // Вытаскиваем значение элемента
            int value = array[left];
            // Перемещаемся по элементам, которые перед вытащенным элементом
            int i = left - 1;
            for (; i >= 0; i--) {
                // Если вытащили значение меньшее — передвигаем больший элемент дальше
                if (value < array[i]) {
                    array[i + 1] = array[i];
                } else {
                    // Если вытащенный элемент больше — останавливаемся
                    break;
                }
            }
            // В освободившееся место вставляем вытащенное значение
            array[i + 1] = value;
        }
    }
}