package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Proiect 2025");

        if (args.length < 2) {
            System.out.println("Usage: java Main <pathType> <filePath>");
            return;
        }

        PathTypes pathType;
        try {
            pathType = PathTypes.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid PathType: " + args[0]);
            return;
        }

        String filePath = args[1];
        Database database = Database.getInstance();

        // Procesăm fișierul în funcție de tipul său
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                CommandProcessor.processCommand(pathType, line, database);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
