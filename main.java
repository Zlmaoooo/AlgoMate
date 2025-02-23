import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            try {
                System.out.println("\nWelcome to Java Calculator!");
                System.out.print("Enter first number: ");
                double num1 = scanner.nextDouble();

                System.out.print("Enter an operator (+, -, *, /): ");
                char operator = scanner.next().charAt(0);

                System.out.print("Enter second number: ");
                double num2 = scanner.nextDouble();

                double result = calculate(num1, num2, operator);
                System.out.printf("Result: %.2f%n", result);
                
                System.out.print("\nDo you want to perform another calculation? (y/n): ");
                char choice = scanner.next().charAt(0);
                if (choice != 'y' && choice != 'Y') {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter valid numbers.");
                scanner.nextLine(); // Clear the scanner buffer
            }
        }
        
        scanner.close();
    }

    private static double calculate(double num1, double num2, char operator) {
        switch (operator) {
            case '+': return num1 + num2;
            case '-': return num1 - num2;
            case '*': return num1 * num2;
            case '/': 
                if (num2 == 0) {
                    throw new ArithmeticException("Division by zero!");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator!");
        }
    }
}
