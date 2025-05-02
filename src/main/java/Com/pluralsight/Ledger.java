package Com.pluralsight;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Ledger {
    private static final String FILE_PATH = "transactions.csv";

    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(parseTransaction(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }

        // Sort by date descending (newest first)
        transactions.sort((t1, t2) -> t2.getDateTime().compareTo(t1.getDateTime()));
        return transactions;
    }

    private static Transaction parseTransaction(String line) {
        String[] parts = line.split(Pattern.quote("|"));
        return new Transaction(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
    }

    public static void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.format("%s|%s|%s|%s|%.2f\n",
                    transaction.getDateTime().toLocalDate(),
                    transaction.getDateTime().toLocalTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount()));
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public static void displayTransactions(List<Transaction> transactions) {
        System.out.println(Transaction.getFormattedLedgerTextHeader());
        transactions.forEach(t -> System.out.println(t.getFormattedLedgerText()));
    }

    public static void showLedgerScreen() {
        // Implementation for ledger screen navigation
    }
}
