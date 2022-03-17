// Отобразить в окне консоли аргументы командной строки в обратном порядке.

package Unit01;

import java.util.Scanner;

public class A2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        for (int i = 0; true; i++) {
            String argument = new String(input.nextLine());
            argument.reverse();
            System.out.println("Hello " + argument.toString()); 
        }
    }
}
