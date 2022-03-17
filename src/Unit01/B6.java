// Простые числа.

package Unit01;

import java.util.Scanner;

public class B6 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Ввести простое число : ");
        int n = s.nextInt();
        if (isPrime(n)) {
            System.out.println(n + " простое число");
        } else {
            System.out.println(n + " не простое число");
        }
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
