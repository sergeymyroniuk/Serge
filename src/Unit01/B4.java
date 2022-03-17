// Числа, которые делятся на 5 и на 7.

package Unit01;

import java.util.Scanner;

public class B4 {
    public static void main(String[] args) {
        System.out.print("Сколько целых чисел вы собираетесь ввести? ");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] sourceNumbers = new int[size];
        for (int i = 0; i < size; i++)
            if ((sourceNumbers[i] % 5 == 0) && (sourceNumbers[i] % 7 == 0))
                System.out.println("Число, которое делится на 5 и 7: " + sourceNumbers[i]);
    }
}
