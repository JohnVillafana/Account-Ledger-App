package Com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    private static final Console console = new Console();
    private static final List<Transaction> transactions = Ledger.loadTransactions();

    public static void main(String[] args) {
        System.out.println("Welcome to the Accounting Ledger App!");
        showHomeScreen();
    }

    public static void showHomeScreen() {
        boolean running = true;

        while (running) {
            System.out.println("\nHOME SCREEN");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String choice = console.promptForString("Enter your choice: ").toUpperCase();

            switch (choice) {
                case "D":
                    addTransaction(true);
                    break;
                case "P":
                    addTransaction(false);
                    break;
                case "L":
                    Ledger.showLedgerScreen();
                    break;
                case "X":
                    running = false;
                    System.out.println("Thank you for using the Accounting Ledger App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addTransaction(boolean isDeposit) {
        System.out.println("\n" + (isDeposit ? "ADD DEPOSIT" : "MAKE PAYMENT"));

        String description = console.promptForString("Description: ");
        String vendor = console.promptForString("Vendor: ");
        double amount = console.promptForDouble("Amount: ");

        if (!isDeposit) {
            amount = -Math.abs(amount); // Ensure payment is negative
        }

        Transaction transaction = new Transaction(
                LocalDate.now().toString(),
                LocalTime.now().toString(),
                description,
                vendor,
                amount);

        Ledger.saveTransaction(transaction);
        transactions.addFirst(transaction); // Add to beginning of list

        System.out.println("Transaction saved successfully!");
    }
}