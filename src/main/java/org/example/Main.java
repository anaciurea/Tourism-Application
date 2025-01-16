package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Proiect 2025");

        // Verificăm dacă există suficiente argumente
        if (args.length < 2) {
            System.out.println("Usage: java Main <inputFilePath> <outputFilePath>");
            return;
        }

        // Preluăm căile fișierelor din argumente
        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String exceptionsPath = outputFilePath.replace(".out", "_exceptions.out");

        // Verificăm dacă fișierul de intrare există
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.err.println("Error: Input file does not exist: " + inputFilePath);
            return;
        }

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
                BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFilePath));
                BufferedWriter exceptionsWriter = new BufferedWriter(new FileWriter(exceptionsPath))
        ) {
            Database database = Database.getInstance();

            // Sărim peste prima linie din fișier (antet sau comentariu)
            String firstLine = reader.readLine();
            System.out.println("Skipping first line: " + firstLine);

            // Citim fiecare linie din fișier și o procesăm
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    // Procesăm linia și scriem rezultatul în fișierul de ieșire
                    String result = CommandProcessor.processCommand(line, database);
                    if (result != null && !result.isEmpty()) {
                        outputWriter.write(result);
                        outputWriter.newLine();
                    }
                } catch (Exception e) {
                    // Scriem excepțiile în fișierul de excepții
                    exceptionsWriter.write(e.getMessage());
                    exceptionsWriter.newLine();
                }
            }

            System.out.println("Processing complete. Results written to: " + outputFilePath);

        } catch (IOException e) {
            // Gestionăm erorile de citire/scriere
            System.err.println("Error reading or writing files: " + e.getMessage());
        }
    }
}
