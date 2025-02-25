import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class AdvancedCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder input;
    private ArrayList<String> history;
    private double memory;
    private static final Set<String> UNARY_FUNCTIONS = new HashSet<>();

    static {
        UNARY_FUNCTIONS.add("sin");
        UNARY_FUNCTIONS.add("cos");
        UNARY_FUNCTIONS.add("tan");
        UNARY_FUNCTIONS.add("log");
        UNARY_FUNCTIONS.add("sqrt");
        UNARY_FUNCTIONS.add("cot");
        UNARY_FUNCTIONS.add("sec");
        UNARY_FUNCTIONS.add("csc");
    }
    
    public AdvancedCalculator() {
        setTitle("Advanced Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        input = new StringBuilder();
        history = new ArrayList<>();
        memory = 0.0;
        
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 5, 5));
        
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sin", "cos", "tan", "log",
                "sqrt", "^", "M+", "MR",
                "H", "C", "(", ")",
                "DEL", "cot", "sec", "csc"
        };
        
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);
            panel.add(button);
        }
        
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("C")) {
            input.setLength(0);
            display.setText("");
        } else if (command.equals("DEL")) {
            if (input.length() > 0) {
                input.delete(input.length() - 1, input.length());
                display.setText(input.toString());
            }
        } else if (command.equals("=")) {
            try {
                double result = evaluateExpression(input.toString());
                history.add(input.toString() + " = " + result);
                display.setText(String.valueOf(result));
                input.setLength(0);
            } catch (Exception ex) {
                display.setText("Error");
                input.setLength(0);
            }
        } else if (command.equals("M+")) {
            try {
                memory = Double.parseDouble(display.getText());
            } catch (NumberFormatException ex) {
                display.setText("Error");
            }
        } else if (command.equals("MR")) {
            display.setText(String.valueOf(memory));
        } else if (command.equals("H")) {
            JOptionPane.showMessageDialog(this, String.join("\n", history), "Calculation History", JOptionPane.INFORMATION_MESSAGE);
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
                    case "cot": stack.push(1 / Math.tan(Math.toRadians(a))); break;
                    case "sec": stack.push(1 / Math.cos(Math.toRadians(a))); break;
                    case "csc": stack.push(1 / Math.sin(Math.toRadians(a))); break;
                    case "+": stack.push(stack.pop() + a); break;
                    case "-": stack.push(stack.pop() - a); break;
                    case "*": stack.push(stack.pop() * a); break;
                    case "/": stack.push(stack.pop() / a); break;
                    case "^": stack.push(Math.pow(stack.pop(), a)); break;
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
            case '^' -> 3;
            default -> 0;
        };
    }
    
    public static void main(String[] args) {
        new AdvancedCalculator();
    }
}