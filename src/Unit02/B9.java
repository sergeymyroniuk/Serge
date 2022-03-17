package Unit02;

import java.util.Scanner;

public class B9 {
    public static void main(String[] args) {
        System.out.print("Введите число: ");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        if (number == 1){
            System.out.println("January");
        }
        if (number == 2){
            System.out.println("February");
        }
        if (number == 3){
            System.out.println("March");
        }
        if (number == 4){
            System.out.println("April");
        }
        if (number == 5){
            System.out.println("May");
        }
        if (number == 6){
            System.out.println("June");
        }
        if (number == 7){
            System.out.println("Jule");
        }
        if (number == 8){
            System.out.println("August");
        }
        if (number == 9){
            System.out.println("September");
        }
        if (number == 10){
            System.out.println("October");
        }
        if (number == 11){
            System.out.println("November");
        }
        if (number == 12){
            System.out.println("December");
        }
        else {
            System.out.println("Такого месяца не существует в природе!");
        }
    }
}
