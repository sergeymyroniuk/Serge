import javax.swing.*;
import java.awt.*;

public class Task {
    public static void main(String[] args) {
        Call r = new Call("Перевод чисел из 10-ой 2-ой 8-ой 16-ой систем в любую");
        r.setVisible(true);
        r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        r.setSize(530, 500);
        r.setResizable(false);
        r.setLocationRelativeTo(null);
        r.getContentPane().setBackground(Color.cyan);
    }
}
