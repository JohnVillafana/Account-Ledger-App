package Com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Main {
    private final Console console;
    private final Ledger ledger;
    private final Report report;

    public Main() {
        this.console = new Console();
        this.ledger = new Ledger(console);
        this.report = new Report(console, ledger);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        console.printHeader("ACCOUNTING LEDGER APPLICATION");

        try {
            boolean running = true;
            while (running) {
                running = showMainMenu();
            }
        } finally {
            console.close();
        }

        console.printSuccess("Goodbye! Thank you for using Accounting Ledger");
    }

    private boolean showMainMenu() {
        console.printHeader("MAIN MENU");
        System.out.println("D) Add Deposit");
        System.out.println("P) Add Payment");
        System.out.println("L) View Ledger");
        System.out.println("R) Reports");
        System.out.println("X) Exit");

        String choice = console.promptForString("Enter your choice: ").toUpperCase();

        switch (choice) {
            case "D" -> {
                addTransaction(true);
                return true;
            }
            case "P" -> {
                addTransaction(false);
                return true;
            }
            case "L" -> {
                showLedgerMenu();
                return true;
            }
            case "R" -> {
                report.showReportsMenu();
                return true;
            }
            case "X" -> {
                return false;
            }
            default -> {
                console.printError("Invalid choice");
                return true;
            }
        }
    }

    private void addTransaction(boolean isDeposit) {
        console.printHeader(isDeposit ? "ADD DEPOSIT" : "ADD PAYMENT");

        LocalDate date = console.promptForDate("Enter date");
        LocalTime time = console.promptForTime("Enter time");
        String description = console.promptForString("Enter description: ");
        String vendor = console.promptForString("Enter vendor: ");
        double amount = console.promptForDouble("Enter amount: ");

        if (!isDeposit) {
            amount = -Math.abs(amount);
        }

        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        ledger.saveTransaction(transaction);
    }

    private void showLedgerMenu() {
        boolean viewing = true;
        while (viewing) {
            console.printHeader("LEDGER MENU");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("H) Home");

            String choice = console.promptForString("Enter your choice: ").toUpperCase();

            switch (choice) {
                case "A" -> ledger.displayAllTransactions();
                case "D" -> ledger.displayDeposits();
                case "P" -> ledger.displayPayments();
                case "H" -> viewing = false;
                default -> console.printError("Invalid choice");
            }
        }
    }
}