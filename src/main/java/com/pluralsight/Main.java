package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static String fileName;
    private static Scanner scanner = new Scanner(System.in);
    private static FileManager fileManager = new FileManager(fileName);
    private static Console console = new Console();

    public static void main(String[] args) {
        fileName = "transactions.csv";
        fileManager = new FileManager(fileName);
        transactions = fileManager.readTransactionsFromFile();
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

        do {
            option = console.promptForString(userPromptOptions);

            if (option.equalsIgnoreCase("D")) {
                addDeposit();
            } else if (option.equalsIgnoreCase("P")) {
                addPayment();
            } else if (option.equalsIgnoreCase("L")) {
                ledgerScreen();
            }
        } while (!option.equalsIgnoreCase("x"));
    }

    private static void addDeposit() {

        LocalDate date = null;
        LocalTime time = LocalTime.now();

        String dateInput = console.promptForString("Enter date (YYYY-MM-DD): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        date = LocalDate.parse(dateInput, formatter);

        String description = console.promptForString("Enter description: ");

        String vendor = console.promptForString("Enter Vendor: ");

        double amount =  console.promptForDouble("Enter Deposit Amount: ");

        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);

        fileManager.writeTransactionsToFile(transactions);

        System.out.println(newTransaction.getEncodedText());

    }

    private static void addPayment() {
        LocalDate date = null;
        LocalTime time = LocalTime.now();

        String dateInput = console.promptForString("Enter date (YYYY-MM-DD): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        date = LocalDate.parse(dateInput, formatter);

        String description = console.promptForString("Enter description: ");

        String vendor = console.promptForString("Enter Vendor: ");

        double amount =  console.promptForDouble("Amount you would like to pay: ");

        Transaction newTransaction = new Transaction(date, time, description, vendor, -amount);
        transactions.add(newTransaction);

        fileManager.addTransactionToFile(newTransaction);
        //fileManager.writeTransactionsToFile(transactions);

        System.out.println(newTransaction.getEncodedText());


    }

    private static void ledgerScreen() {

        String userPromptOptions = "Please choose an option you would like to move forward with:\n" +
                "A - All\n" +
                "D - Deposit\n" +
                "P - Payment\n" +
                "R - Report\n" +
                "H - Home\n";
        String option;

        do {
            option = console.promptForString(userPromptOptions);

            if (option.equalsIgnoreCase("A")) {
                displayAllEntries();
            } else if (option.equalsIgnoreCase("D")) {
                displayEntriesDeposits();
            } else if (option.equalsIgnoreCase("P")) {
                displayEntriesPayments();
            } else if (option.equalsIgnoreCase("R")) {
                reportOptions();
            }
        } while (!option.equalsIgnoreCase("H"));
    }

    private static void displayAllEntries() {
        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions){
            System.out.println(t.getFormattedTransactionText());
        }
    }

    private static void displayEntriesDeposits() {
        System.out.println("Deposits Entries");
        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions){
            if (t.getAmount() > 0){
                System.out.println(t.getFormattedTransactionText());
            }
        }
    }

    private static void displayEntriesPayments() {
        System.out.println("Payments Entries");
        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions){
            if (t.getAmount() < 0){
                System.out.println(t.getFormattedTransactionText());
            }
        }
    }

    private static void reportOptions() {
        System.out.println("Reports");
        String userPromptOptions = "Please choose an option you would like to move forward with:\n" +
                "1 - Month To Date\n" +
                "2 - Previous Month\n" +
                "3 - Year To Date\n" +
                "4 - Previous Year\n" +
                "5 - Search by Vendor\n" +
                "0 - Back\n";
        int option;

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
            }
        } while (option != 0);

    }

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

    private static void searchByVendor(){
        String vendorSearch = console.promptForString("Enter vendor name to search: ");

        System.out.println(Transaction.getFormattedBookTextHeader());
        for (Transaction t : transactions){
            if (t.getVendor().equalsIgnoreCase(vendorSearch)){
                System.out.println(t.getFormattedTransactionText());
            }
        }
    }
}