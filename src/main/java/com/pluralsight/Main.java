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

        bufWriter.write("The amout diposited is: " + amount);

        bufWriter.close();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}

    private static void makePayment(){

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("debit_info.csv");
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);


            System.out.println("Enter your Debit information below: ");
            System.out.print("Name on card: ");
            String cardHolderName = scanner.nextLine();
            System.out.print("Card Number: ");
            long cardNumber = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Enter Expiration Date (MM/YY): ");
            String expirationDate = scanner.nextLine();
            System.out.print("Enter CVV (3-4 digit code): ");
            int cvvCode = scanner.nextInt();
            scanner.nextLine();

            bufWriter.write("Name on Card:\n" + cardHolderName +
                    "\nCard Number:\n" + cardNumber + "\nExpiration Date:\n" + expirationDate +
                    "\nCVV:\n" + cvvCode);
            bufWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


}

    private static void displayLedger(){
    System.out.println("Ledger In progresss.....");
}
}