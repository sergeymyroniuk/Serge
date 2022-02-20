

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Call extends JFrame {
    JButton b1;                                         // ссылка на объект (компонент) нашего окна (кнопка)
    JButton b2;
    JButton b3;
    JButton b4;
    eHandler handler = new eHandler();                  // ссылка для прикрепления слушателя к кнопке

    public Call(String s) {
        super(s);
        setLayout(new FlowLayout());
        b1 = new JButton("Перевод из десятичной системы счисления");
        b2 = new JButton("Перевод из двоичной системы счисления");
        b3 = new JButton("Перевод из восьмеричной системы счисления");
        b4 = new JButton("Перевод из шестнадцатеричной системы счисления");
        add(b1);                                         // добавление элемента в JFrame (в окно) кнопки
        add(b2);
        add(b3);
        add(b4);
        b2.addActionListener(handler);
        b1.addActionListener(handler);
        b3.addActionListener(handler);
        b4.addActionListener(handler);
    }

    public class eHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b1) {
                Ten r = new Ten("Перевод чисел из десятичной системы в любую систему счисления");
                r.setVisible(true);
                r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                r.setSize(530, 500);
                r.setResizable(false);
                r.setLocationRelativeTo(null);
                r.getContentPane().setBackground(Color.magenta);
                }
                if (e.getSource() == b2) {
                    Two r = new Two("Перевод чисел из двоичной системы в любую систему счисления");
                    r.setVisible(true);
                    r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    r.setSize(530, 500);
                    r.setResizable(false);
                    r.setLocationRelativeTo(null);
                    r.getContentPane().setBackground(Color.pink);
                }
            if (e.getSource() == b3) {
                Eight r = new Eight("Перевод чисел из восьмеричной системы в любую систему счисления");
                r.setVisible(true);
                r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                r.setSize(530, 500);
                r.setResizable(false);
                r.setLocationRelativeTo(null);
                r.getContentPane().setBackground(Color.red);
            }
            if (e.getSource() == b4) {
                Sixteen r = new Sixteen("Перевод чисел из шестнадцатеричной системы в любую систему счисления");
                r.setVisible(true);
                r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                r.setSize(530, 500);
                r.setResizable(false);
                r.setLocationRelativeTo(null);
                r.getContentPane().setBackground(Color.YELLOW);
            }
        }
    }
}
