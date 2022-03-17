package Unit02;

import java.util.Scanner;

public class A1 {
    public static void main(String[] args) {
        int max = 0;
        int min = 100;
        String minStr = null;
        String maxStr = null;

        System.out.println("Enter data");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        String [] str = new String[size];

        for (int i = 0; i < size; i++) {
            str[i] = scanner.nextLine();
        }

        for (int i = 0; i < size; i++) {
            if (str[i].length() <= min){
                min = str[i].length();
                minStr = str[i];
            }

            if (str[i].length() > max) {
                max = str[i].length();
                maxStr = str[i];
            }

        }

        System.out.println("max - "+maxStr +"  --> "+max+"\n" +  "min - " + minStr +"  --> "+min);
    }
}
