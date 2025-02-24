import java.util.Scanner;

public class ScientificCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        double num1, num2, result;

        while (true) {
            displayMenu();
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Clear invalid input
                continue;
            }
            
            choice = scanner.nextInt();

            if (choice >= 1 && choice <= 4) {
                System.out.print("Enter first number: ");
                num1 = scanner.nextDouble();
                System.out.print("Enter second number: ");
                num2 = scanner.nextDouble();
            } else if (choice >= 5 && choice <= 8) {
                System.out.print("Enter the number: ");
                num1 = scanner.nextDouble();
                num2 = 0; // Not needed for these operations
            } else if (choice == 9) {
                System.out.println("Exiting calculator.");
                break;
            } else {
                System.out.println("Invalid choice! Please select a valid operation.");
                continue;
            }

            switch (choice) {
                case 1: result = num1 + num2; System.out.printf("Result: %.2f\n", result); break;
                case 2: result = num1 - num2; System.out.printf("Result: %.2f\n", result); break;
                case 3: result = num1 * num2; System.out.printf("Result: %.2f\n", result); break;
                case 4: 
                    if (num2 == 0) {
                        System.out.println("Error! Division by zero is not allowed.");
                    } else {
                        result = num1 / num2;
                        System.out.printf("Result: %.2f\n", result);
                    }
                    break;
                case 5: System.out.printf("sin(%.2f) = %.2f\n", num1, Math.sin(Math.toRadians(num1))); break;
                case 6: System.out.printf("cos(%.2f) = %.2f\n", num1, Math.cos(Math.toRadians(num1))); break;
                case 7: System.out.printf("tan(%.2f) = %.2f\n", num1, Math.tan(Math.toRadians(num1))); break;
                case 8: 
                    if (num1 <= 0) {
                        System.out.println("Error! Logarithm of non-positive numbers is undefined.");
                    } else {
                        System.out.printf("log10(%.2f) = %.2f\n", num1, Math.log10(num1));
                    }
                    break;
            }
        }
        
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\nScientific Calculator");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Sine (sin)");
        System.out.println("6. Cosine (cos)");
        System.out.println("7. Tangent (tan)");
        System.out.println("8. Logarithm (log base 10)");
        System.out.println("9. Exit");
        System.out.print("Select an operation: ");
    }
}
