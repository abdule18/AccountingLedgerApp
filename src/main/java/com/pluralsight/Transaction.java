package com.pluralsight;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction {
    private LocalDateTime date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDateTime date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }



    public String getEncodedText(){
        return this.date + "|" + this.time + "|" + this.description + "|" + this.vendor + "|" + this.amount;
    }

    public static String getFormattedBookTextHeader(){
        return "DATE | TIME | DESCRIPTION | VENDOR | AMOUNT\n+" +
                "+-- - ---- - ----------- - ------- -------+";
    }
}
