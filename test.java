import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class test {
    private JFrame frame;
    private JTextField display;
    private JPanel buttonPanel;
    private String currentInput = "";
    private boolean isNewInput = true;
    private boolean isDarkTheme = true; // current theme state
    private JButton themeToggleButton;
    private JButton historyButton;
    private List<JButton> buttonList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();

    public test() {
        // Frame styling
        frame = new JFrame("Modern Scientific Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 680);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30));  // dark background

        // Top panel: display and control buttons (theme toggle and history)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(frame.getContentPane().getBackground());

        // Display field styling
        display = new JTextField();
        display.setFont(new Font("SansSerif", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(0, 200, 0), 2, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        topPanel.add(display, BorderLayout.CENTER);

        // Create a panel for the control buttons on the top right
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlPanel.setOpaque(false);
        // Theme toggle button
        themeToggleButton = new JButton("Toggle Theme");
        themeToggleButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        themeToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleTheme();
            }
        });
        controlPanel.add(themeToggleButton);

        // History button
        historyButton = new JButton("History");
        historyButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistory();
            }
        });
        controlPanel.add(historyButton);
        topPanel.add(controlPanel, BorderLayout.EAST);
        frame.add(topPanel, BorderLayout.NORTH);

        // Button panel configuration
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 5, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Define buttons (functions, numbers, operators, and extra keys for parentheses)
        String[] buttons = {
            "sin", "cos", "tan", "log", "sqrt",
            "(", ")", "^", "%", "C",
            "7", "8", "9", "/", "*",
            "4", "5", "6", "-", "+",
            "1", "2", "3", "0", "="
        };

        // Create and style the buttons
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("SansSerif", Font.BOLD, 22));
            button.setFocusPainted(false);
            button.setBorder(new LineBorder(new Color(60, 60, 60), 2, true));
            button.addActionListener(new ButtonClickListener());
            // Save the button for theme updates later
            buttonList.add(button);
            buttonPanel.add(button);
        }
        
        updateTheme(); // set initial theme
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    // Toggles between dark and light themes
    private void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        updateTheme();
    }
    
    // Updates UI components based on the current theme
    private void updateTheme() {
        if (isDarkTheme) {
            // Dark Theme Settings
            frame.getContentPane().setBackground(new Color(30, 30, 30));
            buttonPanel.setBackground(new Color(30, 30, 30));
            display.setBackground(new Color(20, 20, 20));
            display.setForeground(new Color(0, 255, 0));
            themeToggleButton.setBackground(new Color(60, 60, 60));
            themeToggleButton.setForeground(Color.WHITE);
            historyButton.setBackground(new Color(60, 60, 60));
            historyButton.setForeground(Color.WHITE);
            
            // Update buttons
            for (JButton button : buttonList) {
                String text = button.getText();
                if ("sin cos tan log sqrt".contains(text)) {
                    button.setBackground(new Color(70, 130, 180)); // steel blue for functions
                    button.setForeground(Color.WHITE);
                } else if ("C".equals(text)) {
                    button.setBackground(new Color(220, 20, 60)); // crimson for clear
                    button.setForeground(Color.WHITE);
                } else if ("=".equals(text)) {
                    button.setBackground(new Color(34, 139, 34)); // forest green for equals
                    button.setForeground(Color.WHITE);
                } else if ("()+-/*^%".contains(text)) {
                    button.setBackground(new Color(105, 105, 105)); // dim gray for operators/parentheses
                    button.setForeground(Color.WHITE);
                } else { 
                    // Number buttons
                    button.setBackground(new Color(245, 245, 245));
                    button.setForeground(Color.BLACK);
                }
            }
        } else {
            // Light Theme Settings (white, grey and red accents)
            frame.getContentPane().setBackground(new Color(240, 240, 240));
            buttonPanel.setBackground(new Color(240, 240, 240));
            display.setBackground(Color.WHITE);
            display.setForeground(Color.BLACK);
            themeToggleButton.setBackground(new Color(211, 211, 211));
            themeToggleButton.setForeground(Color.BLACK);
            historyButton.setBackground(new Color(211, 211, 211));
            historyButton.setForeground(Color.BLACK);
            
            // Update buttons for light theme
            for (JButton button : buttonList) {
                String text = button.getText();
                if ("sin cos tan log sqrt".contains(text)) {
                    button.setBackground(new Color(211, 211, 211)); // light grey for functions
                    button.setForeground(Color.BLACK);
                } else if ("C".equals(text)) {
                    button.setBackground(new Color(255, 69, 0)); // orange red for clear
                    button.setForeground(Color.WHITE);
                } else if ("=".equals(text)) {
                    button.setBackground(new Color(192, 192, 192)); // silver for equals
                    button.setForeground(Color.BLACK);
                } else if ("()+-/*^%".contains(text)) {
                    button.setBackground(new Color(211, 211, 211)); // light grey for operators/parentheses
                    button.setForeground(Color.BLACK);
                } else { 
                    // Number buttons
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                }
            }
        }
    }
    
    // Displays calculation history in a pop-up dialog.
    private void showHistory() {
        if (historyList.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No history available.", "History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder historyText = new StringBuilder();
            for (String record : historyList) {
                historyText.append(record).append("\n");
            }
            JTextArea textArea = new JTextArea(historyText.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(350, 400));
            JOptionPane.showMessageDialog(frame, scrollPane, "History", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("C")) {
                currentInput = "";
                display.setText("");
            } else if (command.equals("=")) {
                try {
                    double result = evaluateExpression(currentInput);
                    String record = currentInput + " = " + result;
                    historyList.add(record);
                    display.setText(String.valueOf(result));
                    isNewInput = true;
                } catch (Exception ex) {
                    display.setText("Error");
                    currentInput = "";
                }
            } else {
                if (isNewInput) {
                    currentInput = "";
                    isNewInput = false;
                }
                currentInput += command;
                display.setText(currentInput);
            }
        }
    }

    // Process functions then evaluate the arithmetic expression.
    private double evaluateExpression(String expression) {
        try {
            expression = ExpressionEvaluator.processFunctions(expression);
            return MathEvaluator.evaluate(expression);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Expression");
        }
    }
    
    public static void main(String[] args) {
        new test();
    }
}

class ExpressionEvaluator {
    public static String processFunctions(String expression) {
        String[] functions = {"sin", "cos", "tan", "log", "sqrt"};
        for (String func : functions) {
            while (expression.contains(func)) {
                int funcIndex = expression.indexOf(func);
                int startIndex = expression.indexOf("(", funcIndex);
                if (startIndex == -1) {
                    throw new RuntimeException("Missing opening parenthesis for function " + func);
                }
                int endIndex = findClosingParen(expression, startIndex);
                if (endIndex == -1) {
                    throw new RuntimeException("Missing closing parenthesis for function " + func);
                }
                String innerExpr = expression.substring(startIndex + 1, endIndex);
                double innerValue = MathEvaluator.evaluate(innerExpr);
                double result = 0;
                switch (func) {
                    case "sin":
                        result = Math.sin(Math.toRadians(innerValue));
                        break;
                    case "cos":
                        result = Math.cos(Math.toRadians(innerValue));
                        break;
                    case "tan":
                        result = Math.tan(Math.toRadians(innerValue));
                        break;
                    case "log":
                        result = Math.log10(innerValue);
                        break;
                    case "sqrt":
                        result = Math.sqrt(innerValue);
                        break;
                }
                String funcCall = expression.substring(funcIndex, endIndex + 1);
                expression = expression.replace(funcCall, Double.toString(result));
            }
        }
        return expression;
    }
    
    private static int findClosingParen(String str, int openPos) {
        int count = 0;
        for (int i = openPos; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(')
                count++;
            else if (c == ')') {
                count--;
                if (count == 0)
                    return i;
            }
        }
        return -1;
    }
}

class MathEvaluator {
    public static double evaluate(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() &&
                       (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                numbers.push(Double.parseDouble(sb.toString()));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%') {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(c);
            }
        }
        while (!operators.isEmpty()) {
            numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
        }
        return numbers.pop();
    }

    private static int precedence(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '*': case '/': case '%': return 2;
            case '^': return 3;
        }
        return 0;
    }

    private static double applyOperator(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            case '%': return a % b;
            case '^': return Math.pow(a, b);
        }
        return 0;
    }
}
