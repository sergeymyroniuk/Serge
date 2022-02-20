

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Eight extends JFrame {
    JButton b1;                                         // ссылка на объект (компонент) нашего окна (кнопка)
    JButton b2;                                         // ссылка на объект (компонент) нашего окна (кнопка)
    JButton b3;
    JButton b4;
    JButton b5;
    JButton b6;
    JButton b7;
    JLabel l1;                                          // ссылка на объект (компонент) нашего окна (строки), которая будет отображаться в окне
    JLabel l2;                                          // ссылка на объект (компонент) нашего окна (строки), которая будет отображаться в окне
    JTextField t1;                                      // ссылка на объект (компонент) нашего окна (поле ввода)
    int i;                                              // переменная, которая получится в результате ввода числа в поле t1 строку
    String x;
    String a;                                           // строковая переменная, которая получит свое значение после вычисления
    String b;
    eHandler handler = new eHandler();                  // ссылка для прикрепления слушателя к кнопке

    public Eight(String s) {
        super(s);
        setLayout(new FlowLayout());
        l1 = new JLabel("Введите восьмеричное число:");
        b5 = new JButton("Перевод чисел из десятичной системы в любую систему счисления");
        b1 = new JButton("Перевод в двоичную систему счисления");
        b2 = new JButton("Перевод в десятичную систему счисления");
        b3 = new JButton("Перевод в шестнадцатеричную систему счисления");
        b4 = new JButton("Очистить");
        b6 = new JButton("Перевод чисел из двоичной системы в любую систему счисления");
        b7 = new JButton("Перевод чисел из шестнадцатеричной системы в любую систему счисления");
        t1 = new JTextField(10);                 // создание объект поле ввода (10 - это количество цифр, которые можно в него ввести)
        l2 = new JLabel("");                        // создание объект JLabel с пустой строкой, которая затем появится в результате вычисления
        add(l1);
        add(t1);
        add(b4);
        add(b1);                                         // добавление элемента в JFrame (в окно) кнопки
        add(b2);                                         // добавление элемента в JFrame (в окно) кнопки
        add(b3);
        add(b5);
        add(b6);
        add(b7);
        add(l2);
        b1.addActionListener(handler);
        b3.addActionListener(handler);
        b2.addActionListener(handler);
        b4.addActionListener(handler);
        b5.addActionListener(handler);
        b6.addActionListener(handler);
        b7.addActionListener(handler);
    }

    public class eHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == b1) {
                    b = Integer.toBinaryString(Integer.parseInt(t1.getText(), 8));
                    a = "Двоичная система счисления: " + b;
                    l2.setText(a);
                }
                if (e.getSource() == b2) {
                    i = Integer.parseInt(t1.getText(),8);
                    a = "Десятичная система счисления: " + i;
                    l2.setText(a);
                }
                if (e.getSource() == b3) {
                    x = Integer.toHexString(Integer.parseInt(t1.getText(), 8));
                    a = "Шестнадцатеричная система счисления: " + x.toUpperCase();
                    l2.setText(a);
                }
                if (e.getSource() == b4) {
                    t1.setText(null);
                    l2.setText("");
                }
                if (e.getSource() == b5) {
                    Ten r = new Ten("Перевод чисел из десятичной системы в любую систему счисления");
                    r.setVisible(true);
                    r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    r.setSize(530, 500);
                    r.setResizable(false);
                    r.setLocationRelativeTo(null);
                    r.getContentPane().setBackground(Color.magenta);
                }
                if (e.getSource() == b5) {
                    Two r = new Two("Перевод чисел из двоичной системы в любую систему счисления");
                    r.setVisible(true);
                    r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    r.setSize(530, 500);
                    r.setResizable(false);
                    r.setLocationRelativeTo(null);
                    r.getContentPane().setBackground(Color.pink);
                }
                if (e.getSource() == b7) {
                    Sixteen r = new Sixteen("Перевод чисел из шестнадцатеричной системы в любую систему счисления");
                    r.setVisible(true);
                    r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    r.setSize(530, 500);
                    r.setResizable(false);
                    r.setLocationRelativeTo(null);
                    r.getContentPane().setBackground(Color.YELLOW);
                }
            }catch (Exception ex){ JOptionPane.showMessageDialog(null, "Введите в поле число!"); }
        }
    }
}


