package com.pluralsight;

import java.util.ArrayList;

public class FileManager {
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Transaction> readTransactionsFromFile(){


        return null;
    }
}
