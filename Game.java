import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class Game extends JFrame {

    private JPanel contentPanel;
    private JButton startButton;
    private JTextField playerHealthField;
    private JButton okPlayerButton;
    private JTextField computerHealthField;
    private JButton okComputerButton;
    private JTextField maxStrategiesField;
    private JButton okStrategiesButton;
    private JLabel background;
    private JButton continueButton;
    private JLabel userHealthLabel;
    private JLabel computerHealthLabel;
    private JLabel optimalStrategyLabel;
    private JLabel lastUserStrategyLabel;
    private JLabel lastComputerStrategyLabel;
    private int[] strategyUsageCount;
    private int[] computerStrategyUsageCount;
    private int[][] gameMatrix = {
            {0, -2, -4, 0, -6},
            {2, 0, -2, 0, -4},
            {4, 2, 0, 0, -2},
            {0, 0, 0, 0, 7},
            {6, 4, 2, -7, 0}
    };
    private boolean gameStarted = false;
    private int currentRound = 1;

    public Game() {
        setTitle("Игра");
        setSize(1130, 679);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(((ImageIcon) background.getIcon()).getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        background = new JLabel(new ImageIcon("images/spouse_conflict.jpg"));
        contentPanel.add(background);  // Добавим фон на панель
        startButton = new JButton("<html><div style='text-align: center;'>Почати<br>гру</div></html>");
        startButton.setPreferredSize(new Dimension(200, 100));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.GRAY);

        Font timesNewRomanFont = new Font("Times New Roman", Font.PLAIN, 20);
        startButton.setFont(timesNewRomanFont);
        strategyUsageCount = new int[5];
        computerStrategyUsageCount = new int[5];
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.remove(startButton);
                addPlayerHealthField();
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        contentPanel.setLayout(null);
        contentPanel.add(startButton);
        startButton.setBounds(20, 70, 200, 100);

        add(contentPanel);
        setVisible(true);
    }

    private void removeComponents() {
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addPlayerHealthField() {
        JLabel playerLabel = new JLabel("Введіть здоров'я гравця:");
        playerHealthField = new JTextField();
        okPlayerButton = new JButton("ОК");

        playerLabel.setBounds(20, 10, 250, 30);
        playerHealthField.setBounds(20, 40, 100, 30);
        okPlayerButton.setBounds(130, 40, 50, 30);
        okPlayerButton.setEnabled(false);

        playerHealthField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState(okPlayerButton, playerHealthField);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState(okPlayerButton, playerHealthField);
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState(okPlayerButton, playerHealthField);
            }
        });

        okPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addComputerHealthField();
            }
        });

        contentPanel.add(playerLabel);
        contentPanel.add(playerHealthField);
        contentPanel.add(okPlayerButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addComputerHealthField() {
        JLabel computerLabel = new JLabel("Введіть здоров'я комп'ютера:");
        computerHealthField = new JTextField();
        okComputerButton = new JButton("ОК");

        computerLabel.setBounds(20, 80, 250, 30);
        computerHealthField.setBounds(20, 110, 100, 30);
        okComputerButton.setBounds(130, 110, 50, 30);
        okComputerButton.setEnabled(false);

        computerHealthField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState(okComputerButton, computerHealthField);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState(okComputerButton, computerHealthField);
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState(okComputerButton, computerHealthField);
            }
        });

        okComputerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStrategiesField();
            }
        });

        contentPanel.add(computerLabel);
        contentPanel.add(computerHealthField);
        contentPanel.add(okComputerButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addStrategiesField() {
        JLabel strategiesLabel = new JLabel("Введіть кількість використання стратегій:");
        maxStrategiesField = new JTextField();
        okStrategiesButton = new JButton("ОК");

        strategiesLabel.setBounds(20, 150, 300, 30);
        maxStrategiesField.setBounds(20, 180, 100, 30);
        okStrategiesButton.setBounds(130, 180, 50, 30);
        okStrategiesButton.setEnabled(false);

        maxStrategiesField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState(okStrategiesButton, maxStrategiesField);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState(okStrategiesButton, maxStrategiesField);
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateButtonState(okStrategiesButton, maxStrategiesField);
            }
        });

        okStrategiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeComponents();
                addContinueButton();
            }
        });

        contentPanel.add(strategiesLabel);
        contentPanel.add(maxStrategiesField);
        contentPanel.add(okStrategiesButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addContinueButton() {
        continueButton = new JButton("Продовжити");
        continueButton.setBounds(20, 220, 200, 30);

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeComponents();

                background.setIcon(new ImageIcon("images/spouse_conflict+Table.jpg"));
                int rounds = Integer.parseInt(maxStrategiesField.getText()) * 5;
                playRounds(rounds);
                addQuestionLabel();
                addStrategyButtons();
                addStatusLabels();
            }
        });

        contentPanel.add(continueButton);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addQuestionLabel() {
        JLabel questionLabel = new JLabel("Хочете дізнатися найкращу стратегію?");
        questionLabel.setBounds(20, 20, 400, 30);
        contentPanel.add(questionLabel);

        JButton yesButton = new JButton("Так");
        JButton noButton = new JButton("Ні");

        yesButton.setBounds(20, 50, 80, 30);
        noButton.setBounds(110, 50, 80, 30);

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOptimalStrategy();
                // После нажатия "Да" выключаем только эту кнопку
                yesButton.setEnabled(false);
                noButton.setEnabled(false);
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRoundNumberAndActivateButtons();
                // После нажатия "Нет" выключаем только эту кнопку
                noButton.setEnabled(false);
                yesButton.setEnabled(false);
            }
        });

        contentPanel.add(yesButton);
        contentPanel.add(noButton);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void playStrategy(String strategy) {
        int strategyIndex = getStrategyIndex(strategy);

        if (strategyIndex != -1) {
            // Проверка количества использований стратегии
            if (strategyUsageCount[strategyIndex] >= Integer.parseInt(maxStrategiesField.getText())) {
                // Количество использований достигло максимального значения, игнорируем стратегию
                return;
            }

            strategyUsageCount[strategyIndex]++;

            int computerStrategy = chooseComputerStrategy();
            int playerPayoff = gameMatrix[strategyIndex][computerStrategy];
            int computerPayoff = gameMatrix[computerStrategy][strategyIndex];

            displayRoundResults(strategyIndex, computerStrategy, playerPayoff, computerPayoff);

            updatePlayerAndComputerHealth(playerPayoff, computerPayoff);
            checkGameEndCondition();
            updateRemainingComputerUsageLabels();
            updateRemainingUsageLabels();

            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    private void updateRemainingUsageLabels() {

        for (int i = 0; i < strategyUsageCount.length; i++) {
            JLabel strategyLabel = (JLabel) contentPanel.getComponent(8 + i); // Adjust the starting index as needed
            strategyLabel.setText("У вас залишилось " + (i + 1+"-ї") +" стратегії: " + (Integer.parseInt(maxStrategiesField.getText()) - strategyUsageCount[i]));
        }


        for (int i = 0; i < computerStrategyUsageCount.length; i++) {
            JLabel computerStrategyLabel = (JLabel) contentPanel.getComponent(8 + strategyUsageCount.length + i); // Adjust the starting index as needed
            computerStrategyLabel.setText("У комп'ютера залишилося " + (i + 1+"-ї") +" стратегії: " + (Integer.parseInt(maxStrategiesField.getText()) - computerStrategyUsageCount[i]));
        }
    }

    private void updateRemainingComputerUsageLabels() {

        for (int i = 0; i < computerStrategyUsageCount.length; i++) {
            JLabel computerStrategyLabel = (JLabel) contentPanel.getComponent(8 + strategyUsageCount.length + i); // Adjust the starting index as needed
            computerStrategyLabel.setText("Стратегія " + (i + 1) + ": " + (Integer.parseInt(maxStrategiesField.getText()) - computerStrategyUsageCount[i]));
        }
    }

    private int chooseComputerStrategy() {
        int totalStrategies = gameMatrix.length;
        int maxStrategyUses = Integer.parseInt(maxStrategiesField.getText());


        List<Integer> availableStrategies = new ArrayList<>();
        for (int i = 0; i < totalStrategies; i++) {
            if (computerStrategyUsageCount[i] < maxStrategyUses) {
                availableStrategies.add(i);
            }
        }

        if (!availableStrategies.isEmpty()) {

            int randomIndex = (int) (Math.random() * availableStrategies.size());
            int chosenStrategy = availableStrategies.get(randomIndex);
            computerStrategyUsageCount[chosenStrategy]++;

            return chosenStrategy;
        } else {
            return (int) (Math.random() * totalStrategies);
        }
    }

    private void displayRoundResults(int playerStrategy, int computerStrategy, int playerPayoff, int computerPayoff) {
        lastUserStrategyLabel.setText("Ваша стратегія в останньому раунді: " + (playerStrategy + 1));
        lastComputerStrategyLabel.setText("Стратегія комп'ютера в останньому раунді: " + (computerStrategy + 1));

    }


    private void updatePlayerAndComputerHealth(int playerPayoff, int computerPayoff) {
        int playerHealth = Integer.parseInt(playerHealthField.getText());
        int computerHealth = Integer.parseInt(computerHealthField.getText());


        playerHealth = (playerPayoff < 0) ? Math.max(playerHealth + playerPayoff, 0) : playerHealth;

        computerHealth = (computerPayoff < 0) ? Math.max(computerHealth + computerPayoff, 0) : computerHealth;

        playerHealthField.setText(Integer.toString(playerHealth));
        computerHealthField.setText(Integer.toString(computerHealth));

        userHealthLabel.setText("Здоров'я гравця: " + playerHealth);
        computerHealthLabel.setText("Здоров'я комп'ютера: " + computerHealth);


        if (playerHealth <= 0 || computerHealth <= 0) {
            endGame();
        }
    }

    private void checkGameEndCondition() {
        int rounds = Integer.parseInt(maxStrategiesField.getText()) * 5;

        if (currentRound < rounds) {
            currentRound++;
            showRoundNumberAndActivateButtons();
        } else {
            endGame();
        }
    }

    private void endGame() {
        int playerHealth = Integer.parseInt(playerHealthField.getText());
        int computerHealth = Integer.parseInt(computerHealthField.getText());

        if (playerHealth > computerHealth) {
            JOptionPane.showMessageDialog(this, "Гра завершена! Ви виграли!");
        } else if (playerHealth < computerHealth) {
            JOptionPane.showMessageDialog(this, "Гра завершена! Ви програли. Комп'ютер виграв!");
        } else {
            JOptionPane.showMessageDialog(this, "Гра завершена! Нічиє!");
        }
    }

    private void showRoundNumberAndActivateButtons() {

        Component[] components = contentPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel && ((JLabel) component).getText().startsWith("<html><font color='red'>РАУНД:")) {
                contentPanel.remove(component);
            }
        }

        JLabel roundLabel = new JLabel("<html><font color='red'>РАУНД:" + currentRound + "</font></html>");
        roundLabel.setFont(new Font("Arial", Font.BOLD, 30));
        roundLabel.setBounds(contentPanel.getWidth() / 2 - 70, contentPanel.getHeight() / 2 - 20, 150, 40);
        contentPanel.add(roundLabel);

        activateStrategyButtons();

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showOptimalStrategy() {
        int optimalStrategyIndex = findOptimalStrategyForPlayer1();
        optimalStrategyLabel = new JLabel("Найкраща стратегія для гравця: " + (optimalStrategyIndex + 1));
        optimalStrategyLabel.setBounds(20, 90, 400, 30);

        // Вывод номера раунда
        JLabel roundLabel = new JLabel("<html><font color='red'>РАУНД:" + currentRound + "</font></html>");
        roundLabel.setFont(new Font("Arial", Font.BOLD, 30));
        roundLabel.setBounds(contentPanel.getWidth() / 2 - 70, contentPanel.getHeight() / 2 - 20, 150, 40);
        contentPanel.add(roundLabel);

        // Активация кнопок стратегии игрока 1
        activateStrategyButtons();

        contentPanel.add(optimalStrategyLabel);

        contentPanel.revalidate();
        contentPanel.repaint();

        lastUserStrategyLabel.setText("Ваша стратегія в останньому раунді: ");
        lastComputerStrategyLabel.setText("Стратегія комп'ютера в останньому раунді:");

    }

    private void activateStrategyButtons() {
        gameStarted = true; // Устанавливаем флаг начала игры
        Component[] components = contentPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                int strategyIndex = getStrategyIndex(button.getText());

                // Проверка, что стратегия еще не использована максимальное количество раз
                if (strategyIndex >= 0 && strategyUsageCount[strategyIndex] < Integer.parseInt(maxStrategiesField.getText())) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        }
    }

    private int findOptimalStrategyForPlayer1() {
        int rows = gameMatrix.length;
        int cols = gameMatrix[0].length;

        int maxMinValue = Integer.MIN_VALUE;
        int optimalStrategyIndex = 0;

        for (int i = 0; i < rows; i++) {
            int minInRow = Integer.MAX_VALUE;

            for (int j = 0; j < cols; j++) {
                minInRow = Math.min(minInRow, gameMatrix[i][j]);
            }

            maxMinValue = Math.max(maxMinValue, minInRow);

            if (minInRow == maxMinValue) {
                optimalStrategyIndex = i;
            }
        }

        return optimalStrategyIndex;
    }

    private void playRounds(int rounds) {
        // Здесь вы можете реализовать логику игры для указанного количества раундов
        // Например, вы можете использовать цикл for для проведения раундов и вызывать методы для каждого раунда.
        for (int i = 0; i < rounds; i++) {
            // Логика для каждого раунда
        }
    }

    private void addStrategyButtons() {
        addButtonWithText("Кидок однією тарілкою", 10, 300, 180, 30);
        addButtonWithText("Кидок трьома тарілками", 10, 350, 180, 30);
        addButtonWithText("Кидок п'ятьма тарілками", 10, 400, 180, 30);
        addButtonWithText("Захист підносом", 10, 450, 180, 30);
        addButtonWithText("Кидок чайником", 10, 500, 180, 30);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addButtonWithText(String buttonText, int x, int y, int width, int height) {
        JButton strategyButton = new JButton(buttonText);
        strategyButton.setBounds(x, y, width, height);

        int strategyIndex = getStrategyIndex(buttonText);

        strategyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playStrategy(buttonText);
            }
        });

        // Установим состояние кнопок в зависимости от количества использований
        strategyButton.setEnabled(gameStarted && strategyUsageCount[strategyIndex] < Integer.parseInt(maxStrategiesField.getText()));

        contentPanel.add(strategyButton);
    }

    // Добавьте метод для получения индекса стратегии по названию
    private int getStrategyIndex(String strategy) {
        switch (strategy) {
            case "Кидок однією тарілкою":
                return 0;
            case "Кидок трьома тарілками":
                return 1;
            case "Кидок п'ятьма тарілками":
                return 2;
            case "Захист підносом":
                return 3;
            case "Кидок чайником":
                return 4;
            default:
                return -1; // Если стратегия не найдена
        }
    }

    private void updateButtonState(JButton button, JTextField textField) {
        try {
            int value = Integer.parseInt(textField.getText());
            if (value >= 0 && !textField.getText().startsWith("0")) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
        } catch (NumberFormatException ex) {
            button.setEnabled(false);
        }
    }

    private void addStatusLabels() {
        userHealthLabel = new JLabel("Здоров'я гравця: " + playerHealthField.getText());
        computerHealthLabel = new JLabel("Здоров'я комп'ютера: " + computerHealthField.getText());

        lastUserStrategyLabel = new JLabel("Ваша стратегія в останньому раунді:");
        lastComputerStrategyLabel = new JLabel("Стратегія комп'ютера в останньому раунді:");

        userHealthLabel.setBounds(845, 20, 300, 30);
        computerHealthLabel.setBounds(845, 50, 300, 30);

        lastUserStrategyLabel.setBounds(845, 80, 300, 30);
        lastComputerStrategyLabel.setBounds(845, 110, 300, 30);

        // Add labels for remaining player strategy usage counts
        for (int i = 0; i < strategyUsageCount.length; i++) {
            JLabel strategyLabel = new JLabel("У вас залишилось " + (i + 1+"-ї") +" стратегії: " + (Integer.parseInt(maxStrategiesField.getText()) - strategyUsageCount[i]));
            strategyLabel.setBounds(845, 140 + i * 30, 300, 30);
            contentPanel.add(strategyLabel);
        }

        // Add labels for remaining computer strategy usage counts
        for (int i = 0; i < computerStrategyUsageCount.length; i++) {
            JLabel computerStrategyLabel = new JLabel("У комп'ютера залишилося " + (i + 1+"-ї") +" стратегії: " + (Integer.parseInt(maxStrategiesField.getText()) - computerStrategyUsageCount[i]));
            computerStrategyLabel.setBounds(845, 140 + (strategyUsageCount.length + i) * 30, 300, 30);
            contentPanel.add(computerStrategyLabel);
        }

        contentPanel.add(lastUserStrategyLabel);
        contentPanel.add(lastComputerStrategyLabel);

        contentPanel.add(userHealthLabel);
        contentPanel.add(computerHealthLabel);


        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
