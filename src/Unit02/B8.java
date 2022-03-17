package Unit02;

import java.util.Scanner;

public class B8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите число: " );
        int s = sc.nextInt();
        System.out.println("Система счисления: " + Integer.toHexString(s));
    }
}
