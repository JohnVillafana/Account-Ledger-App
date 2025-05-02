package Com.pluralsight;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class Report {
    public static void showReportsScreen(List<Transaction> transactions) {
        Console console = new Console();
        boolean running = true;

        while (running) {
            System.out.println("\nREPORTS SCREEN");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back to Ledger");

            String choice = console.promptForString("Enter your choice: ");

            switch (choice) {
                case "1":
                    showMonthToDate(transactions);
                    break;
                case "2":
                    showPreviousMonth(transactions);
                    break;
                case "3":
                    showYearToDate(transactions);
                    break;
                case "4":
                    showPreviousYear(transactions);
                    break;
                case "5":
                    searchByVendor(transactions);
                    break;
                case "6":
                    customSearch(transactions);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMonthToDate(List<Transaction> transactions) {
        LocalDate now = LocalDate.now();
        List<Transaction> filtered = transactions.stream()
                .filter(t -> t.getDateTime().toLocalDate().getMonth() == now.getMonth()
                        && t.getDateTime().toLocalDate().getYear() == now.getYear())
                .collect(Collectors.toList());
        Ledger.displayTransactions(filtered);
    }

    private static void customSearch(List<Transaction> transactions) {
        Console console = new Console();

        System.out.println("\nCUSTOM SEARCH");
        String startDate = console.promptForString("Start Date (YYYY-MM-DD, leave blank to skip): ");
        String endDate = console.promptForString("End Date (YYYY-MM-DD, leave blank to skip): ");
        String description = console.promptForString("Description (leave blank to skip): ");
        String vendor = console.promptForString("Vendor (leave blank to skip): ");
        String amount = console.promptForString("Amount (leave blank to skip): ");

        List<Transaction> results = transactions.stream()
                .filter(t -> startDate.isEmpty() ||
                        t.getDateTime().toLocalDate().isAfter(LocalDate.parse(startDate)))
                .filter(t -> endDate.isEmpty() ||
                        t.getDateTime().toLocalDate().isBefore(LocalDate.parse(endDate)))
                .filter(t -> description.isEmpty() ||
                        t.getDescription().toLowerCase().contains(description.toLowerCase()))
                .filter(t -> vendor.isEmpty() ||
                        t.getVendor().toLowerCase().contains(vendor.toLowerCase()))
                .filter(t -> amount.isEmpty() ||
                        Double.parseDouble(amount) == t.getAmount())
                .collect(Collectors.toList());

        Ledger.displayTransactions(results);
    }

    // Other report methods implemented similarly...
}
