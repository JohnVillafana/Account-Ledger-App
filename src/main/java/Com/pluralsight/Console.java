package Com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Console {
    private final Scanner scanner = new Scanner(System.in);

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    public String promptForString(String prompt) {
        while (true) {
            System.out.print(BLUE + prompt + RESET);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            printError("Input cannot be empty");
        }
    }

    public double promptForDouble(String prompt) {
        while (true) {
            try {
                System.out.print(BLUE + prompt + RESET);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                printError("Please enter a valid number");
            }
        }
    }

    public LocalDate promptForDate(String prompt) {
        while (true) {
            try {
                System.out.print(BLUE + prompt + " (YYYY-MM-DD): " + RESET);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                printError("Invalid date format. Example: 2023-04-15");
            }
        }
    }

    public LocalTime promptForTime(String prompt) {
        while (true) {
            try {
                System.out.print(BLUE + prompt + " (HH:MM:SS): " + RESET);
                return LocalTime.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                printError("Invalid time format. Example: 14:30:00");
            }
        }
    }

    public void printError(String message) {
        System.out.println(RED + "[!] " + message + RESET);
    }

    public void printSuccess(String message) {
        System.out.println(GREEN + "[✓] " + message + RESET);
    }

    public void printHeader(String header) {
        System.out.println("\n" + YELLOW + "=== " + header + " ===" + RESET);
    }

    public void close() {
        scanner.close();
    }
}