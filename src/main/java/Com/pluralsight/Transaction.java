package Com.pluralsight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Transaction {
    private final LocalDateTime dateTime;
    private final String description;
    private final String vendor;
    private final double amount;

    public Transaction(String date, String time, String description, String vendor, double amount) {
        // Validate inputs
        Objects.requireNonNull(date, "Date cannot be null");
        Objects.requireNonNull(time, "Time cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.vendor = Objects.requireNonNull(vendor, "Vendor cannot be null");

        // Parse date/time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        this.dateTime = LocalDateTime.parse(date + "|" + time, formatter);

        // Validate amount
        if (Double.isNaN(amount)) {
            throw new IllegalArgumentException("Amount cannot be NaN");
        }
        this.amount = amount;
    }

    // Immutable getters
    public LocalDateTime getDateTime() {
        return dateTime; // Return defensive copy
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    // Formatted output methods
    public String getFormattedLedgerText() {
        return String.format("%-12s %-8s %-20s %-20s $%.2f",
                dateTime.toLocalDate(),
                dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                description,
                vendor,
                amount);
    }

    public static String getFormattedLedgerTextHeader() {
        return String.format("%-12s %-8s %-20s %-20s %s",
                "Date", "Time", "Description", "Vendor", "Amount");
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%.2f",
                dateTime.toLocalDate(),
                dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                description,
                vendor,
                amount);
    }
}
