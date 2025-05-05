# Accounting Ledger Application

![Java](https://img.shields.io/badge/Java-23-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

A command-line accounting system for tracking financial transactions with CSV persistence.

## Features

- 💰 **Transaction Management**
    - Record deposits and payments
    - Automatic date/time tracking
    - Vendor and description fields

- 📊 **Ledger Views**
    - All transactions (newest first)
    - Filter by deposits only
    - Filter by payments only

- 📈 **Financial Reports**
    - Month-to-date
    - Previous month 
    - Year-to-date
    - Vendor search

- 💾 **Data Persistence**
    - All data saved to `transactions.csv`
    - Automatic file creation
    - Data validation on load

### Home Screen
![Homescreen.jpg](src/main/resources/Homescreen.jpg)

### Ledger Screen

![LedgerMenu.jpg](src/main/resources/LedgerMenu.jpg)
### Report Screen
![ReportMenu.jpg](src/main/resources/ReportMenu.jpg)
### Intersting Piece of Code
The custom search functionality represented my most significant technical challenge and growth opportunity in this project. I deliberately avoided pre-built solutions, choosing instead to architect the entire system from first principles. The algorithm processes natural language queries with contextual awareness, automatically extracting transactional keywords (like merchant names or dollar amounts) while discarding non-essential terms. For instance, when users input "Amazon purchase on May 5th", the system intelligently isolates "Amazon" and the date parameters to deliver precise results.




