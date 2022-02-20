package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ProgramCalculator extends JFrame {
    double num1;
    double num2;
    double numResult;

    double a12;
    int b12;

    double a0;




    JButton clear;
    JButton equals;
    JButton b31;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;
    JButton b6;
    JButton b7;
    JButton b8;
    JButton b9;
    JButton b10;
    JButton b11;
    JButton plus;
    JButton minus;
    JButton multiplication;
    JButton division;
    JButton precente;
    JButton root;
    JButton degree;
    JButton negative;
    JTextField t1;
    JLabel l1;
    JLabel l2;
    eHandler handler = new eHandler();

    public ProgramCalculator(String calculator) {
        super(calculator);
        setLayout(new FlowLayout());

        clear = new JButton("C");
        equals = new JButton("=");
        b31 = new JButton("←");
        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");
        b10 = new JButton(".");
        b11 = new JButton("0");
        plus = new JButton("+");
        minus = new JButton("-");
        multiplication = new JButton("*");
        division = new JButton("/");
        precente = new JButton("%");
        root = new JButton("sqrt");
        degree = new JButton("^");
        negative = new JButton("+/-");
        t1 = new JTextField(10);
        l1 = new JLabel("");
        l2 = new JLabel("");

        add(t1);
        add(l2);
        add(clear);
        add(equals);
        add(b31);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(b7);
        add(b8);
        add(b9);
        add(b11);
        add(b10);
        add(plus);
        add(minus);
        add(multiplication);
        add(division);
        add(precente);
        add(root);
        add(degree);
        add(negative);
        clear.addActionListener(handler);
        equals.addActionListener(handler);
        b31.addActionListener(handler);
        b1.addActionListener(handler);
        b2.addActionListener(handler);
        b3.addActionListener(handler);
        b4.addActionListener(handler);
        b5.addActionListener(handler);
        b6.addActionListener(handler);
        b7.addActionListener(handler);
        b8.addActionListener(handler);
        b9.addActionListener(handler);
        b10.addActionListener(handler);
        b11.addActionListener(handler);
        plus.addActionListener(handler);
        minus.addActionListener(handler);
        multiplication.addActionListener(handler);
        division.addActionListener(handler);
        precente.addActionListener(handler);
        root.addActionListener(handler);
        degree.addActionListener(handler);
        negative.addActionListener(handler);

        Dimension t = new Dimension(770, 30);
        t1.setColumns(20);
        clear.setPreferredSize(t);
        Dimension s = new Dimension(80, 60);
        clear.setPreferredSize(s);
        equals.setPreferredSize(s);
        b31.setPreferredSize(s);
        b1.setPreferredSize(s);
        b2.setPreferredSize(s);
        b3.setPreferredSize(s);
        b4.setPreferredSize(s);
        b5.setPreferredSize(s);
        b6.setPreferredSize(s);
        b7.setPreferredSize(s);
        b8.setPreferredSize(s);
        b9.setPreferredSize(s);
        b10.setPreferredSize(s);
        b11.setPreferredSize(s);
        plus.setPreferredSize(s);
        minus.setPreferredSize(s);
        multiplication.setPreferredSize(s);
        division.setPreferredSize(s);
        precente.setPreferredSize(s);
        root.setPreferredSize(s);
        degree.setPreferredSize(s);
        negative.setPreferredSize(s);
    }

    public class eHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b1) {
                t1.setText(t1.getText() + "1");
            }
            if (e.getSource() == b2) {
                t1.setText(t1.getText() + "2");
            }
            if (e.getSource() == b3) {
                t1.setText(t1.getText() + "3");
            }
            if (e.getSource() == b4) {
                t1.setText(t1.getText() + "4");
            }
            if (e.getSource() == b5) {
                t1.setText(t1.getText() + "5");
            }
            if (e.getSource() == b6) {
                t1.setText(t1.getText() + "6");
            }
            if (e.getSource() == b7) {
                t1.setText(t1.getText() + "7");
            }
            if (e.getSource() == b8) {
                t1.setText(t1.getText() + "8");
            }
            if (e.getSource() == b9) {
                t1.setText(t1.getText() + "9");
            }
            if (e.getSource() == b11) {
                t1.setText(t1.getText() + "0");
            }
            if (e.getSource() == b10) {
                t1.setText(t1.getText() + ".");
            }
            //if (e.getSource() == b16) {
            //    t1.setText(t1.getText() + "-");
            //}

            if (e.getSource() == clear) {
                num1 = 0;
                num2 = 0;
                numResult = 0;
                t1.setText("");
                l2.setText("");
            }
            if (e.getSource() == b31) {
                String s = t1.getText();
                t1.setText("");
                for (int i = 0; i < s.length() - 1; i++) {
                    t1.setText(t1.getText() + s.charAt(i));
                }
            }


            if (e.getSource() == plus) {
                num1 = Double.parseDouble(t1.getText());
                t1.setText("");
                l1.setText("+");
                t1.setText(t1.getText() + "");
                l2.setText("+");
            }

            if (e.getSource() == minus) {
                num1 = Double.parseDouble(t1.getText());
                t1.setText("");
                l1.setText("-");
                t1.setText(t1.getText() + "");
                l2.setText("-");
            }

            if (e.getSource() == multiplication) {
                num1 = Double.parseDouble(t1.getText());
                t1.setText("");
                l1.setText("*");
                t1.setText(t1.getText() + "");
                l2.setText("*");
            }

            if (e.getSource() == division) {
                num1 = Double.parseDouble(t1.getText());
                t1.setText("");
                l1.setText("/");
                t1.setText(t1.getText() + "");
                l2.setText("/");
            }
            if (e.getSource() == negative) {
                num1 = Double.parseDouble(t1.getText());
                num1 = -num1;
                t1.setText("");
                DecimalFormat format = new DecimalFormat("0.#");
                t1.setText("" + format.format(num1));
                l1.setText(t1.getText() + "-");
            }
            if (e.getSource() == root) {
                num1 = Double.parseDouble(t1.getText());
                num1 = Math.sqrt(num1);
                DecimalFormat format = new DecimalFormat("0.##########");
                t1.setText("" + format.format(num1));
                l1.setText("sqrt");
            }


            if (e.getSource() == degree) {
                a12 = Double.parseDouble(t1.getText());
                t1.setText("");
                l1.setText("^");

            }
            if (e.getSource() == precente) {
                a0 = Double.parseDouble(t1.getText());
                t1.setText("");
                l1.setText("%");
                t1.setText(t1.getText() + "");

            }


            if (e.getSource() == equals) {
                if (l1.getText() == "+") {
                    num2 = Double.parseDouble(t1.getText());
                    String a = Double.toString(num1);
                    String b = Double.toString(num2);
                    BigDecimal one = new BigDecimal(a);
                    BigDecimal two = new BigDecimal(b);
                    one = one.add(two);
                    DecimalFormat format = new DecimalFormat("0.##########");
                    t1.setText("" + format.format(one));
                    l1.setText("+");
                    l2.setText("+");
                }
                if (l1.getText() == "-") {
                    num2 = Double.parseDouble(t1.getText());
                    String a = Double.toString(num1);
                    String b = Double.toString(num2);
                    BigDecimal one = new BigDecimal(a);
                    BigDecimal two = new BigDecimal(b);
                    one = one.subtract(two);
                    DecimalFormat format = new DecimalFormat("0.##########");
                    t1.setText("" + format.format(one));
                    l1.setText("-");
                    l2.setText("-");
                }
                if (l1.getText() == "*") {
                    num2 = Double.parseDouble(t1.getText());
                    String a = Double.toString(num1);
                    String b = Double.toString(num2);
                    BigDecimal one = new BigDecimal(a);
                    BigDecimal two = new BigDecimal(b);
                    one = one.multiply(two);
                    DecimalFormat format = new DecimalFormat("0.##########");
                    t1.setText("" + format.format(one));
                    l1.setText("*");
                    l2.setText("*");
                }
                if (l1.getText() == "/") {
                    num2 = Double.parseDouble(t1.getText());
                    String a = Double.toString(num1);
                    String b = Double.toString(num2);
                    BigDecimal one = new BigDecimal(a);
                    BigDecimal two = new BigDecimal(b);
                    one = one.divide(two);
                    DecimalFormat format = new DecimalFormat("0.##########");
                    t1.setText("" + format.format(one));
                    l1.setText("/");
                    l2.setText("/");
                }
                if (l1.getText() == "^") {
                    b12 = Integer.parseInt(t1.getText());
                    t1.setText("" + Math.pow(a12, b12));
                    l1.setText("");
                    l2.setText("^");
                }
                if (l1.getText() == "%") {
                    double b0 = Double.parseDouble(t1.getText());
                    double c0;
                    c0= a0/100 * b0;
                    t1.setText("" + c0);
                    l1.setText("");
                    l2.setText("%");
                }
            }
        }
    }
}
