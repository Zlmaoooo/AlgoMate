import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CalculatorGUI {
    private static JTextField textField; // This is the text field where numbers & results are shown
    private static String currentInput = ""; // Stores the current equation

    public static void main(String[] args) {
        // Create a frame (window)
        JFrame frame = new JFrame("Simple Calculator");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Text field for display
        textField = new JTextField();
        textField.setBounds(20, 20, 240, 40);
        textField.setEditable(false); // Make it read-only
        frame.add(textField);

        // Buttons for numbers & operators
        String[] buttonLabels = {
            "7", "8", "9", "/", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-", 
            "0", "C", "=", "+"
        };

        int x = 20, y = 80;
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setBounds(x, y, 50, 50);
            frame.add(button);

            // Add action listener to buttons
            button.addActionListener(new ButtonClickListener());

            x += 60;
            if (x > 200) { // Move to next row after 4 buttons
                x = 20;
                y += 60;
            }
        }

        frame.setVisible(true);
    }

    // Action Listener for Button Clicks
    static class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String buttonText = ((JButton) e.getSource()).getText();

            if (buttonText.equals("=")) {
                try {
                    currentInput = evaluateExpression(currentInput); // Solve the expression
                } catch (Exception ex) {
                    currentInput = "Error"; // Handle errors
                }
            } else if (buttonText.equals("C")) {
                currentInput = ""; // Clear input
            } else {
                currentInput += buttonText; // Add button text to input
            }

            textField.setText(currentInput); // Update display
        }
    }

    // Simple expression evaluator
    private static String evaluateExpression(String expression) {
        try {
            // Remove spaces from the expression
            expression = expression.replaceAll("\\s", "");

            // Evaluate the expression using JavaScript engine
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return engine.eval(expression).toString();
        } catch (ScriptException e) {
            return "Error";
        }
    }
}
