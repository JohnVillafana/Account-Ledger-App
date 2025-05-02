
package Com.pluralsight;

import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    public String promptForString(String prompt) {
        System.out.print(CYAN + prompt + RESET);
        return scanner.nextLine().trim();
    }

    public double promptForDouble(String prompt) {
        while (true) {
            try {
                System.out.print(CYAN + prompt + RESET);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                printError("Invalid input. Please enter a valid number.");
            }
        }
    }

    public void printError(String message) {
        System.out.println(RED + "[ERROR] " + message + RESET);
    }

    public void printSuccess(String message) {
        System.out.println(GREEN + "[SUCCESS] " + message + RESET);
    }

    public void printWarning(String message) {
        System.out.println(YELLOW + "[WARNING] " + message + RESET);
    }

    public void printHeader(String header) {
        System.out.println(BLUE + "\n=== " + header + " ===" + RESET);
    }

}