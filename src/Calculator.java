package Calculator;

import javax.swing.*;
import java.awt.*;

public class Calculator {
    public static void main(String[] args) {
        ProgramCalculator r = new ProgramCalculator("Calculator");
        r.setVisible(true);
        r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        r.setSize(300,590);
        r.setResizable(false);
        r.setLocationRelativeTo(null);
        r.getContentPane().setBackground(Color.YELLOW);
    }
}
