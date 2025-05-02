package Com.pluralsight;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Ledger {
    private static final String FILE_PATH = "transactions.csv";
    private static final Console console = new Console();

    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        // Check if file exists first
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            console.printWarning("No transaction file found. A new one will be created.");
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;  // Skip empty/commented lines

                try {
                    Transaction transaction = parseTransaction(line);
                    transactions.add(transaction);
                } catch (IllegalArgumentException e) {
                    console.printError("Skipping invalid transaction: " + line);
                    console.printError("Reason: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            console.printError("Fatal error loading transactions: " + e.getMessage());
        }

        // Sort by date descending (newest first)
        transactions.sort(Comparator.comparing(Transaction::getDateTime).reversed());
        return transactions;
    }

    private static Transaction parseTransaction(String line) throws IllegalArgumentException {
        String[] parts = line.split(Pattern.quote("|"));

        if (parts.length != 5) {
            throw new IllegalArgumentException(
                    String.format("Invalid format. Expected 5 fields (date|time|desc|vendor|amount) but got %d", parts.length));
        }

        try {
            return new Transaction(
                    parts[0].trim(),  // date
                    parts[1].trim(),  // time
                    parts[2].trim(),  // description
                    parts[3].trim(),  // vendor
                    Double.parseDouble(parts[4].trim()) // amount
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format: " + parts[4], e);
        }
    }

    public static synchronized void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(transaction.toString());
            writer.newLine();
            console.printSuccess("Transaction saved successfully!");
        } catch (IOException e) {
            console.printError("Failed to save transaction: " + e.getMessage());
        }
    }

    public static void showLedgerScreen() {
        List<Transaction> transactions = loadTransactions();
        boolean running = true;

        while (running) {
            console.printHeader("LEDGER SCREEN");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String choice = console.promptForString("Enter your choice: ").toUpperCase();

            switch (choice) {
                case "A":
                    displayTransactions(transactions);
                    break;
                case "D":
                    displayTransactions(filterDeposits(transactions));
                    break;
                case "P":
                    displayTransactions(filterPayments(transactions));
                    break;
                case "R":
                    Report.showReportsScreen(transactions);
                    break;
                case "H":
                    running = false;
                    break;
                default:
                    console.printError("Invalid option. Please try again.");
            }
        }
    }

    static void displayTransactions(List<Transaction> transactions) {
        console.printHeader("TRANSACTIONS");
        System.out.println(Transaction.getFormattedLedgerTextHeader());
        transactions.forEach(t -> System.out.println(t.getFormattedLedgerText()));
    }

    private static List<Transaction> filterDeposits(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() > 0)
                .toList();
    }

    private static List<Transaction> filterPayments(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .toList();
    }
}
