package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class Main {
    // List to store all transactions
    private static ArrayList<Transaction> transactions;
    private static FileManager fileManager;
    private static final Console console = new Console();

    public static void main(String[] args) {

        // Initialize file name and file manager
        String fileName = "transactions.csv";
        fileManager = new FileManager(fileName);

        // Read all existing transactions from file
        transactions = fileManager.readTransactionsFromFile();
        // call/launch home screen
        homeScreen();
    }

    private static void homeScreen() {
        System.out.println("Hello, Welcome to Java CLI App.");

        String userPromptOptions = "Please choose an option you would like to move forward with:\n" +
                "D - Add Deposit\n" +
                "P - Make Payment (Debit)\n" +
                "L - Ledger\n" +
                "X - Exit\n";
        String option;

        // Looping until user chooses to exit
        do {
            option = console.promptForString(userPromptOptions);

            // Prompt user for an option
            if (option.equalsIgnoreCase("D")) {
                addDeposit();
            } else if (option.equalsIgnoreCase("P")) {
                addPayment();
            } else if (option.equalsIgnoreCase("L")) {
                ledgerScreen();
            }
        //  if option is X Exit everything
        } while (!option.equalsIgnoreCase("X"));
    }

    // This method handles adding deposit transaction
    private static void addDeposit() {


        LocalTime time = LocalTime.now();

        // These variable prompting the user for transaction details using the console class
        LocalDate date = console.promptForDate("Enter date (YYYY-MM-DD): ");

        String description = console.promptForString("Enter description: ");

        String vendor = console.promptForString("Enter Vendor: ");

        double amount =  console.promptForDouble("Enter Deposit Amount: ");

        // These Create new transaction and save it
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);

        fileManager.writeTransactionsToFile(transactions);

        System.out.println(newTransaction.getEncodedText());

    }

    // This method handles adding a payment (debate) transaction
    private static void addPayment() {

        LocalTime time = LocalTime.now();

        // These variable is prompting the user for transaction details using the console class
        LocalDate date = console.promptForDate("Enter date (YYYY-MM-DD): ");

        String description = console.promptForString("Enter description: ");

        String vendor = console.promptForString("Enter Vendor: ");

        double amount =  console.promptForDouble("Amount you would like to pay: ");

        // These Create new transaction and save it, Payments are recorded as negative amounts
        Transaction newTransaction = new Transaction(date, time, description, vendor, -amount);
        transactions.add(newTransaction);

        fileManager.addTransactionToFile(newTransaction);
        //fileManager.writeTransactionsToFile(transactions);

        System.out.println(newTransaction.getEncodedText());


    }

    // This method displays the ledger options
    private static void ledgerScreen() {

        String userPromptOptions = "Please choose an option you would like to move forward with:\n" +
                "A - All\n" +
                "D - Deposit\n" +
                "P - Payment\n" +
                "R - Report\n" +
                "H - Home\n";
        String option;

        //  Loop until user choose to return to home screen
        do {
            option = console.promptForString(userPromptOptions);

            // Prompt user for an option
            if (option.equalsIgnoreCase("A")) {
                displayAllEntries();
            } else if (option.equalsIgnoreCase("D")) {
                displayEntriesDeposits();
            } else if (option.equalsIgnoreCase("P")) {
                displayEntriesPayments();
            } else if (option.equalsIgnoreCase("R")) {
                reportsScreen();
            }
        // if user option H return to  the home screen
        } while (!option.equalsIgnoreCase("H"));
    }

    // This  method displays all transactions
    private static void displayAllEntries() {
        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions){
            System.out.println(t.getFormattedTransactionText());
        }
    }

    // This method displays only deposit transactions
    private static void displayEntriesDeposits() {
        System.out.println("Deposits Entries");
        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions){
            if (t.getAmount() > 0){
                System.out.println(t.getFormattedTransactionText());
            }
        }
    }

    // This method displays only payment transactions
    private static void displayEntriesPayments() {
        System.out.println("Payments Entries");
        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions){
            if (t.getAmount() < 0){
                System.out.println(t.getFormattedTransactionText());
            }
        }
    }

    // This method displays report menu options
    private static void reportsScreen() {
        System.out.println("Reports");
        String userPromptOptions = "Please choose an option you would like to move forward with:\n" +
                "1 - Month To Date\n" +
                "2 - Previous Month\n" +
                "3 - Year To Date\n" +
                "4 - Previous Year\n" +
                "5 - Search by Vendor\n" +
                "6 - Custom Search\n" +
                "0 - Back\n";
        int option;

        // Prompt user for an option
        do {
            option = console.promptForInt(userPromptOptions);

            if (option == 1) {
                displayMonthToDate();
            } else if (option == 2) {
                displayPreviousMonth();
            } else if (option == 3) {
                displayYearToDate();
            } else if (option == 4) {
                displayPreviousYear();
            } else if (option == 5) {
                searchByVendor();
            } else if (option == 6) {
                customSearch();
            }
        // if user option is 0 return to the ledger screen
        } while (option != 0);

    }

    // This method show all transactions from current month
    private static void displayMonthToDate() {
        LocalDate date = LocalDate.now();
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);

        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(firstDayOfMonth)) {
                System.out.println(t.getFormattedTransactionText());
            }
        }

    }

    // This method show all transactions from previous month
    private static void displayPreviousMonth() {
        LocalDate date = LocalDate.now();
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        LocalDate firstDayPrev = firstDayOfMonth.minusMonths(1);
        LocalDate lastDayPrev = firstDayOfMonth.minusDays(1);

        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(firstDayPrev) && !t.getDate().isAfter(lastDayPrev)) {
                System.out.println(t.getFormattedTransactionText());
            }
        }

    }

    // This method show all transactions from current year
    private static void displayYearToDate() {
        LocalDate date = LocalDate.now();
        LocalDate firstDayOfYear = date.withDayOfYear(1);

        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(firstDayOfYear)) {
                System.out.println(t.getFormattedTransactionText());
            }
        }

    }

    // This method show all transactions from previous year
    private static void displayPreviousYear() {
        int lastYear = LocalDate.now().getYear() - 1;
        LocalDate start = LocalDate.of(lastYear,1,1);
        LocalDate end = LocalDate.of(lastYear,12,31);

        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                System.out.println(t.getFormattedTransactionText());
            }
        }

    }

    // This method search for transaction by vendor name
    private static void searchByVendor(){
        String vendorSearch = console.promptForString("Enter vendor name to search: ");

        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions){
            if (t.getVendor().equalsIgnoreCase(vendorSearch)){
                System.out.println(t.getFormattedTransactionText());
            }
        }
    }

    private static void customSearch(){
        String startDateStr = console.promptForString("Enter Start Date (yyyy-mm-dd) or press enter to skip: ");
        String endDateStr = console.promptForString("Enter End Date (yyyy-mm-dd) or press enter to skip: ");
        String description = console.promptForString("Enter Description or press enter to skip: ");
        String vendor = console.promptForString("Enter Vendor or press enter to skip: ");
        String amountStr = console.promptForString("Enter Amount or press enter to skip: ");

        LocalDate startDate = null;
        LocalDate endDate = null;
        Double amount = null;


        try{
            if (!startDateStr.isBlank()) startDate = LocalDate.parse(startDateStr);
            if (!endDateStr.isBlank()) endDate = LocalDate.parse(endDateStr);
            if (!amountStr.isBlank()) amount = Double.parseDouble(amountStr);
        } catch (Exception e) {
            System.out.println("Invalid input. Please make sure date are in yyyy-mm-dd format and amount is a number.");
        }
        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions){
            if ((startDate ==  null || !t.getDate().isBefore(startDate)) && (endDate == null || !t.getDate().isAfter(endDate)) && (description.isBlank() || t.getDescription().equalsIgnoreCase(description)) && (vendor.isBlank() || t.getVendor().equalsIgnoreCase(vendor)) && (amount == null || t.getAmount() == amount)) {
                System.out.println(t.getFormattedTransactionText());
            }
        }
    }
}