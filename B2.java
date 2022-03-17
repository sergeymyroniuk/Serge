package Unit01;

import java.util.Scanner;

public class B2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] sourceNumbers = new int[size];
        int min = sourceNumbers[0];
        for (int i = 0; i < size; i++) {
            if (min > sourceNumbers[i])
                min = sourceNumbers[i];
        }
        System.out.println("Минимльное число:" + min);
    }
}
