package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Console {

    Scanner scanner = new Scanner(System.in);

    // This method is creating a prompt for user int
    public int promptForInt(String prompt){
        boolean hasResult = false;
        int result = -1;
        while(!hasResult){
            try {
                System.out.print(prompt);
                result = scanner.nextInt();
                scanner.nextLine();
                hasResult = true;
            } catch (Exception e) {
                System.out.println("Invalid entry, please try again!");
                scanner.next();
            }
        }

        return result;
    }

    // This method is creating a prompt for user double
    public double promptForDouble(String prompt){
        boolean hasResult = false;
        double result = 0.00;
        while(!hasResult){
            try {
                System.out.print(prompt);
                result = scanner.nextDouble();
                scanner.nextLine();
                hasResult = true;
            } catch (Exception e) {
                System.out.println("Invalid entry, please try again!");
                scanner.next();
            }
        }

        return result;
    }

    // This method is creating a prompt for user date
    public LocalDate promptForDate(String prompt) {
        boolean hasResult = false;
        LocalDate result = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (!hasResult) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                result = LocalDate.parse(input, formatter);
                hasResult = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in YYYY-MM-DD format.");
            }
        }

        return result;
    }

    // This method is creating a prompt for user String
    public String promptForString(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }


}
