package Com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Transaction {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    private final LocalDate date;
    private final LocalTime time;
    private final String description;
    private final String vendor;
    private final double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = Objects.requireNonNull(date, "Date cannot be null");
        this.time = Objects.requireNonNull(time, "Time cannot be null");
        this.description = validateString(description, "Description");
        this.vendor = validateString(vendor, "Vendor");
        this.amount = amount;
    }

    public static Transaction fromCsv(String csvLine) {
        String[] parts = csvLine.split("\\|");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid CSV format. Expected 5 fields");
        }

        return new Transaction(
                LocalDate.parse(parts[0], DATE_FORMATTER),
                LocalTime.parse(parts[1], TIME_FORMATTER),
                parts[2],
                parts[3],
                Double.parseDouble(parts[4])
        );
    }

    public String toCsv() {
        return String.join("|",
                date.format(DATE_FORMATTER),
                time.format(TIME_FORMATTER),
                description,
                vendor,
                String.format("%.2f", amount)
        );
    }

    private String validateString(String value, String fieldName) {
        value = Objects.requireNonNull(value, fieldName + " cannot be null").trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        return value;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getDescription() { return description; }
    public String getVendor() { return vendor; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("%-12s %-8s %-20s %-20s $%.2f",
                date, time, description, vendor, amount);
    }

    public static String getHeader() {
        return String.format("%-12s %-8s %-20s %-20s %s",
                "Date", "Time", "Description", "Vendor", "Amount");
    }
}