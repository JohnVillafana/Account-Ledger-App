package Com.pluralsight;

import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public double promptForDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public LocalDateTime promptForDateTime(String prompt) {
        // Implementation for date/time input
    }
}
