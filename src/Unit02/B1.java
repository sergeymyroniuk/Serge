// Вывести на экран таблицу умножения.

package Unit02;

public class B1 {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++){
            for (int x = 1; x <= 10; x++){
                System.out.printf("%d \t", i * x);
            }
            System.out.printf("\n");
        }
    }
}
