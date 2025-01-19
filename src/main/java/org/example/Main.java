package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Proiect 2025");


        if (args.length < 2) {
            System.out.println("Usage: java Main <inputFilePath> <outputFilePath>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        File inputFile = new File(inputFilePath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.err.println("Error: Input file does not exist: " + inputFilePath);
            return;
        }

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
                BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFilePath))
        ) {
            Database database = Database.getInstance();

            String firstLine = reader.readLine();
            System.out.println("Skipping first line: " + firstLine);

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String result = CommandProcessor.processCommand(line, database);
                    if (result != null && !result.isEmpty()) {
                        outputWriter.write(result);
                        outputWriter.newLine();
                    }
                } catch (IllegalArgumentException e) {
                    outputWriter.write(e.getMessage());
                    outputWriter.newLine();
                } catch (Exception e) {
                    outputWriter.write("Exception: Data is broken. ## (" + line + ")");
                    outputWriter.newLine();
                }

            }
            System.out.println("Processing complete. Results written to: " + outputFilePath);

        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
        }
    }
}
