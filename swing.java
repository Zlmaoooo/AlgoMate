import javax.swing.*;  // Import Swing for GUI

public class swing {
    public static void main(String[] args) {
        // Create a new window (JFrame)
        JFrame frame = new JFrame("Simple Calculator");
        frame.setSize(300, 400); // Set width and height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close when X is clicked
        frame.setLayout(null); // No default layout (we will place components manually)

        // Create a text field to display numbers
        JTextField textField = new JTextField();
        textField.setBounds(20, 20, 240, 40); // Position & size (x, y, width, height)
        frame.add(textField); // Add text field to the window

        // Show the window
        frame.setVisible(true);
    }
}
