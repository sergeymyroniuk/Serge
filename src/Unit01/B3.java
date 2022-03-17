// Числа, которые делятся на 3 или на 9.

package Unit01;

import java.util.Scanner;

public class B3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] sourceNumbers = new int[size];
        for (int i = 0; i < size; i++)
            if ((sourceNumbers[i] % 3 == 0) || (sourceNumbers[i] % 9 == 0))
                System.out.println("Число, которое делится на 3 или 9: " + sourceNumbers[i]);
    }
}
