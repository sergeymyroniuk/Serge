package Unit01;

import java.util.Scanner;

public class A5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ввести целые цисла: ");
        int value = scanner.nextInt();
        int sum = 0;
        int com = 1;
        while (value != 0) {
            sum = sum + value % 10;
            value = value / 10;
        }
        System.out.println("Cумма введённых чисел: " + sum);
        while (value != 0) {
        com *= value % 10;
        value = value / 10;
    }
        System.out.println("Произведение введённых чисел: " + com);
    }
}
