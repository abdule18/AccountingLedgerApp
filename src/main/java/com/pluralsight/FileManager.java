package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FileManager {
    private String fileName;
   //private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public void  addTransactionToFile(Transaction transaction) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);

            bufWriter.write(transaction.getEncodedText());
            bufWriter.newLine();

            bufWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to write transactions to file!");
        }

    }
    public void  writeTransactionsToFile(ArrayList<Transaction> transactions) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);

            Transaction t = transactions.get(transactions.size() - 1);
            bufWriter.write(t.getEncodedText());
            bufWriter.newLine();

            bufWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to write transactions to file!");
        }

    }

    public ArrayList<Transaction> readTransactionsFromFile(){
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);

            BufferedReader reader = new BufferedReader(fr);

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

        LocalDate date = LocalDate.parse(tokens[0]);
        LocalTime time = LocalTime.parse(tokens[1]);
        String description = tokens[2];
        String vendor = tokens[3];
        double amount = Double.parseDouble(tokens[4]);

        return new Transaction(date, time, description, vendor, amount);
    }

//    private static void createFileIfNotExists() {
//        File file = new File(fileName);
//
//        try {
//            if (file.createNewFile()) {
//                System.out.println("transactions.csv created.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred while creating the file.");
//        }
//    }
}
