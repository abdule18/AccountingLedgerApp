package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
        fileManager.readTransactionsFromFile();
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
            System.out.println(userPromptOptions);
            option = scanner.nextLine();

            if (option.equalsIgnoreCase("D")) {
                addDeposit();
            } else if (option.equalsIgnoreCase("P")) {
                makePayment();
            } else if (option.equalsIgnoreCase("L")) {
                displayLedger();
            }
        } while (!option.equalsIgnoreCase("x"));
    }

    private static void addDeposit() {

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String description = console.promptForString("Enter description: ");

        String vendor = console.promptForString("Enter Vendor: ");

        double amount =  console.promptForDouble("Enter Deposit Amount: ");

        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);

        fileManager.writeTransactionsToFile(transactions);

        System.out.println(newTransaction.getEncodedText());

    }

    private static void makePayment() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String description = console.promptForString("Enter description: ");

        String vendor = console.promptForString("Enter Vendor: ");

        double amount =  console.promptForDouble("Amount you would like to pay: ");

        Transaction newTransaction = new Transaction(date, time, description, vendor, -amount);
        transactions.add(newTransaction);

        fileManager.writeTransactionsToFile(transactions);

        System.out.println(newTransaction.getEncodedText());


    }


    private static void displayLedger() {

        String userPromptOptions = "Please choose an option you would like to move forward with:\n" +
                "A - All\n" +
                "D - Deposit\n" +
                "P - Payment\n" +
                "R - Report\n" +
                "H - Home\n";
        String option;

        do {
            System.out.println(userPromptOptions);
            option = scanner.nextLine();

            if (option.equalsIgnoreCase("A")) {
                displayAllEntries();
            } else if (option.equalsIgnoreCase("D")) {
                displayEntriesDeposits();
            } else if (option.equalsIgnoreCase("P")) {
                displayNegativePayments();
            } else if (option.equalsIgnoreCase("R")) {
                reportOptions();
            }
        } while (!option.equalsIgnoreCase("H"));
    }

    private static void displayAllEntries() {
        System.out.println("Display All Entries pending......");
    }

    private static void displayEntriesDeposits() {
        System.out.println("Deposits Entries pending......");
    }

    private static void displayNegativePayments() {
        System.out.println("Negative Payments pending......");
    }

    private static void reportOptions() {
        System.out.println("Reports pending......");
    }

}