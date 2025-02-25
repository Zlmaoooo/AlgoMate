import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ScientificCalculatorGUI extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder input;
    private static final Set<String> UNARY_FUNCTIONS = new HashSet<>();

    static {
        UNARY_FUNCTIONS.add("sin");
        UNARY_FUNCTIONS.add("cos");
        UNARY_FUNCTIONS.add("tan");
        UNARY_FUNCTIONS.add("log");
        UNARY_FUNCTIONS.add("sqrt");
    }
    
    public ScientificCalculatorGUI() {
        setTitle("Scientific Calculator");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.DARK_GRAY);
        
        input = new StringBuilder();
        
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setForeground(Color.BLACK);
        add(display, BorderLayout.NORTH);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 5, 8, 8)); // Increased gap for better separation
        panel.setBackground(Color.LIGHT_GRAY);
        
        String[] buttons = {"ON", "OFF", "H", "%", "÷",
                            "sin", "cos", "tan", "log", "e",
                            "√", "x^y", "x²", "π", "7",
                            "8", "9", "×", "-", "4",
                            "5", "6", "+", "1", "2",
                            "3", "=", "0", ".", "C"};
        
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setPreferredSize(new Dimension(70, 50)); // Square shape for uniformity
            button.addActionListener(this);
            panel.add(button);
        }
        
        add(panel, BorderLayout.CENTER);
        
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.RED);
        JLabel calculatingLabel = new JLabel(" Calculating...... ");
        calculatingLabel.setForeground(Color.WHITE);
        JLabel timeLabel = new JLabel(" Time Took : ");
        timeLabel.setForeground(Color.WHITE);
        footerPanel.add(calculatingLabel, BorderLayout.WEST);
        footerPanel.add(timeLabel, BorderLayout.EAST);
        add(footerPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("C")) {
            input.setLength(0);
            display.setText("");
        } else if (command.equals("=")) {
            try {
                double result = evaluateExpression(input.toString());
                display.setText(String.valueOf(result));
                input.setLength(0);
            } catch (Exception ex) {
                display.setText("Error");
                input.setLength(0);
            }
        } else {
            input.append(command);
            display.setText(input.toString());
        }
    }
    
    private double evaluateExpression(String expression) throws Exception {
        return evaluatePostfix(infixToPostfix(expression));
    }
    
    private String infixToPostfix(String expression) {
        StringBuilder output = new StringBuilder();
        Stack<String> operators = new Stack<>();
        String[] tokens = expression.split("(?<=[-+/^()])|(?=[-+/^()])");
        
        for (String token : tokens) {
            token = token.trim();
            if (token.matches("\\d+(\\.\\d+)?")) {
                output.append(token).append(" ");
            } else if (UNARY_FUNCTIONS.contains(token) || token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.append(operators.pop()).append(" ");
                }
                operators.pop();
            } else {
                while (!operators.isEmpty() && precedence(token.charAt(0)) <= precedence(operators.peek().charAt(0))) {
                    output.append(operators.pop()).append(" ");
                }
                operators.push(token);
            }
        }
        
        while (!operators.isEmpty()) {
            output.append(operators.pop()).append(" ");
        }
        
        return output.toString();
    }
    
    private double evaluatePostfix(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();
        for (String token : postfix.split(" ")) {
            if (token.isEmpty()) continue;
            try {
                stack.push(Double.parseDouble(token));
            } catch (NumberFormatException e) {
                double a = stack.pop();
                switch (token) {
                    case "sin": stack.push(Math.sin(Math.toRadians(a))); break;
                    case "cos": stack.push(Math.cos(Math.toRadians(a))); break;
                    case "tan": stack.push(Math.tan(Math.toRadians(a))); break;
                    case "log": stack.push(Math.log10(a)); break;
                    case "sqrt": stack.push(Math.sqrt(a)); break;
                    case "+": stack.push(stack.pop() + a); break;
                    case "-": stack.push(stack.pop() - a); break;
                    case "*": stack.push(stack.pop() * a); break;
                    case "/": stack.push(stack.pop() / a); break;
                    default: throw new Exception("Invalid Operator");
                }
            }
        }
        return stack.pop();
    }
    
    private int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }
    
    public static void main(String[] args) {
        new ScientificCalculatorGUI();
    }
}
