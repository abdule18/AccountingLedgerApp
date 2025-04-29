package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static String filename;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        filename = "transactions.csv";
        createFileIfNotExists();
        getListedTransaction();
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

        LocalDateTime date = LocalDateTime.now();
        LocalTime time = LocalTime.now();

        System.out.print("Enter a description: ");
        String description = scanner.nextLine();

        System.out.print("Where you depositing from: ");
        String vendor = scanner.nextLine();

        System.out.print("How much would you like to deposit? Please enter an Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);

        transactions.add(newTransaction);
        System.out.println(newTransaction.getFormattedBookTextHeader());
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);

            bufWriter.write(newTransaction.getEncodedText());
            bufWriter.newLine();
            bufWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(newTransaction.getEncodedText());

    }

    private static void createFileIfNotExists() {
        File file = new File(filename);

        try {
            if (file.createNewFile()) {
                System.out.println("transactions.csv created.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
        }
    }

    private static void makePayment() {
        LocalDateTime date = LocalDateTime.now();
        LocalTime time = LocalTime.now();

        System.out.print("Enter a description: ");
        String description = scanner.nextLine();

        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount you would like to pay: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Transaction newTransaction = new Transaction(date, time, description, vendor, -amount);

        transactions.add(newTransaction);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);

            bufWriter.write(newTransaction.getEncodedText());
            bufWriter.newLine();
            bufWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
                repotOptions();
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

    private static void repotOptions() {
        System.out.println("Reports pending......");
    }

    private static ArrayList<Transaction> getListedTransaction() {
        FileReader fr = null;
        try {
            fr = new FileReader(filename);

            BufferedReader reader = new BufferedReader(fr);

            transactions.clear();
            String dataString;

            while ((dataString = reader.readLine()) != null) {
                Transaction transaction = getTransactionFromEncodedString(dataString);
                transactions.add(transaction);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return transactions;

    }

    public static Transaction getTransactionFromEncodedString(String dataString) {
        String[] tokens = dataString.split("\\|");

        LocalDateTime date = LocalDateTime.parse(tokens[0]);
        LocalTime time = LocalTime.parse(tokens[1]);
        String description = tokens[2];
        String vendor = tokens[3];
        double amount = Double.parseDouble(tokens[4]);

        return new Transaction(date, time, description, vendor, amount);
    }
}