package Com.pluralsight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDateTime dateTime;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(String date, String time, String description, String vendor, double amount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        this.dateTime = LocalDateTime.parse(date + "|" + time, formatter);
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDateTime getDateTime() { return dateTime; }
    public String getDescription() { return description; }
    public String getVendor() { return vendor; }
    public double getAmount() { return amount; }

    public String getFormattedLedgerText() {
        return String.format("%-12s %-8s %-20s %-20s $%.2f",
                dateTime.toLocalDate(),
                dateTime.toLocalTime(),
                description,
                vendor,
                amount);
    }

    public static String getFormattedLedgerTextHeader() {
        return String.format("%-12s %-8s %-20s %-20s %s",
                "Date", "Time", "Description", "Vendor", "Amount");
    }
}
