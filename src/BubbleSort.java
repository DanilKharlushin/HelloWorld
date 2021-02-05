import java.lang.reflect.Array;
import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {23, 435, 1, 21321, 12, 4};
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));

    }

    static int[] bubblesort(int[] array) {
        int bubble;
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    bubble = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = bubble;
                }
            }
        }
        return array;
    }

}
