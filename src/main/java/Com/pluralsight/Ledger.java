package Com.pluralsight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Ledger {
    private static final String CSV_HEADER = "date|time|description|vendor|amount";
    private static final Path CSV_PATH = Paths.get("transactions.csv");

    private final Console console;

    public Ledger(Console console) {
        this.console = Objects.requireNonNull(console, "Console cannot be null");
        initializeFile();
    }

    private void initializeFile() {
        try {
            if (!Files.exists(CSV_PATH)) {
                Files.write(CSV_PATH, (CSV_HEADER + "\n").getBytes());
                console.printSuccess("Created new transactions file");
            }
        } catch (IOException e) {
            console.printError("Failed to initialize transactions file: " + e.getMessage());
        }
    }

    public List<Transaction> loadTransactions() {
        try {
            return Files.lines(CSV_PATH)
                    .skip(1) // Skip header
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> {
                        try {
                            return Transaction.fromCsv(line);
                        } catch (Exception e) {
                            console.printError("Skipping invalid transaction: " + line);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(Transaction::getDate)
                            .thenComparing(Transaction::getTime)
                            .reversed())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            console.printError("Failed to load transactions: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public synchronized void saveTransaction(Transaction transaction) {
        try {
            Files.write(CSV_PATH,
                    (transaction.toCsv() + "\n").getBytes(),
                    StandardOpenOption.APPEND);
            console.printSuccess("Transaction saved successfully");
        } catch (IOException e) {
            console.printError("Failed to save transaction: " + e.getMessage());
        }
    }

    public void displayAllTransactions() {
        List<Transaction> transactions = loadTransactions();
        console.printHeader("ALL TRANSACTIONS");
        System.out.println(Transaction.getHeader());
        transactions.forEach(System.out::println);
    }

    public void displayDeposits() {
        List<Transaction> deposits = loadTransactions().stream()
                .filter(t -> t.getAmount() > 0)
                .collect(Collectors.toList());

        console.printHeader("DEPOSITS");
        System.out.println(Transaction.getHeader());
        deposits.forEach(System.out::println);
    }

    public void displayPayments() {
        List<Transaction> payments = loadTransactions().stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.toList());

        console.printHeader("PAYMENTS");
        System.out.println(Transaction.getHeader());
        payments.forEach(System.out::println);
    }
}
