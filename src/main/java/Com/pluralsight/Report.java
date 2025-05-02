package Com.pluralsight;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Report {
    private final Console console;
    private final Ledger ledger;

    public Report(Console console, Ledger ledger) {
        this.console = console;
        this.ledger = ledger;
    }

    public void showReportsMenu() {
        boolean running = true;
        while (running) {
            console.printHeader("REPORTS MENU");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Search by Vendor");
            System.out.println("0) Back to Main Menu");

            String choice = console.promptForString("Enter your choice: ");

            switch (choice) {
                case "1":
                    showMonthToDate();
                    break;
                case "2":
                    showPreviousMonth();
                    break;
                case "3":
                    showYearToDate();
                    break;
                case "4":
                    searchByVendor();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    console.printError("Invalid choice");
            }
        }
    }

    private void showMonthToDate() {
        LocalDate now = LocalDate.now();
        List<Transaction> transactions = ledger.loadTransactions().stream()
                .filter(t -> t.getDate().getMonth() == now.getMonth()
                        && t.getDate().getYear() == now.getYear())
                .collect(Collectors.toList());

        console.printHeader("MONTH TO DATE REPORT");
        System.out.println(Transaction.getHeader());
        transactions.forEach(System.out::println);
    }

    private void showPreviousMonth() {
        LocalDate now = LocalDate.now();
        LocalDate prevMonth = now.minusMonths(1);

        List<Transaction> transactions = ledger.loadTransactions().stream()
                .filter(t -> t.getDate().getMonth() == prevMonth.getMonth()
                        && t.getDate().getYear() == prevMonth.getYear())
                .collect(Collectors.toList());

        console.printHeader("PREVIOUS MONTH REPORT");
        System.out.println(Transaction.getHeader());
        transactions.forEach(System.out::println);
    }

    private void showYearToDate() {
        int currentYear = LocalDate.now().getYear();
        List<Transaction> transactions = ledger.loadTransactions().stream()
                .filter(t -> t.getDate().getYear() == currentYear)
                .collect(Collectors.toList());

        console.printHeader("YEAR TO DATE REPORT");
        System.out.println(Transaction.getHeader());
        transactions.forEach(System.out::println);
    }

    private void searchByVendor() {
        String vendor = console.promptForString("Enter vendor name: ");
        List<Transaction> transactions = ledger.loadTransactions().stream()
                .filter(t -> t.getVendor().equalsIgnoreCase(vendor))
                .collect(Collectors.toList());

        console.printHeader("VENDOR REPORT: " + vendor.toUpperCase());
        System.out.println(Transaction.getHeader());
        transactions.forEach(System.out::println);
    }
}