package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {


        homeScreen();

    }

    private static void homeScreen(){
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

            if (option.equalsIgnoreCase("D")){
                addDeposit();
            } else if (option.equalsIgnoreCase("P")) {
                makePayment();
            } else if (option.equalsIgnoreCase("L")) {
                displayLedger();
            }
        } while (!option.equalsIgnoreCase("x"));
    }

private static void addDeposit(){
    FileWriter fileWriter = null;
    try {
        fileWriter = new FileWriter("deposit_Info.csv");
        BufferedWriter bufWriter = new BufferedWriter(fileWriter);

        System.out.print("How much would you like to deposit? Please enter an Amount: ");
        String  amount = scanner.nextLine();

        bufWriter.write("The amout diposite is: " + amount);

        bufWriter.close();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}

private static void makePayment(){
    System.out.println("Make payment In progresss.....");
}

private static void displayLedger(){
    System.out.println("Ledger In progresss.....");
}
}