import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class Loader extends JFrame implements ActionListener {

    private JButton[] button = new JButton[5];
    private JTextField field1 = new JTextField(15);
    private JTextField field2 = new JTextField(15);
    JPanel calculator = new JPanel();
    double result;

    public Loader() {

        String[] titles = {"+", "-", "*", "/", "pow"};

        JPanel calculator = new JPanel();
        setName("Calculator");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 100));
        pack();

        calculator.add(field1);
        calculator.add(field2);
        setContentPane(calculator);

        for (int j = 0; j < 5; j++) {
            button[j] = new JButton(titles[j]);
//            button[j].setActionCommand(titles[j]);
            calculator.add(button[j]);
            button[j].addActionListener(this);
        }
        field1.addActionListener(this);
        field2.addActionListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double firstArg = Double.parseDouble(field1.getText());
        double secondArg = Double.parseDouble(field2.getText());

        switch (((JButton) e.getSource()).getActionCommand()) {
            case "+":
                result = firstArg + secondArg;
                break;
            case "-":
                result = firstArg - secondArg;
                break;
            case "*":
                result = firstArg * secondArg;
                break;
            case "/":
                result = firstArg / secondArg;
                break;
            case "pow":
                result = Math.pow(firstArg, secondArg);
                break;
        }
        JOptionPane.showInternalMessageDialog(calculator, result, "РЕЗУЛЬТАТ", INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Loader();
    }
}



