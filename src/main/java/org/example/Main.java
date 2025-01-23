package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Proiect 2025");

        if (args.length < 2) {
            System.out.println("Usage: java Main <inputFilePath> <outputFilePath> [<groupsFilePath>] [<eventsFilePath>]");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        File inputFile = new File(inputFilePath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.err.println("Error: Input file does not exist: " + inputFilePath);
            return;
        }


        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFilePath));

        Database database = Database.getInstance();

        String firstLine = reader.readLine();
        System.out.println("Skipping first line: " + firstLine);

        String line;
        while ((line = reader.readLine()) != null) {
            String result = CommandProcessor.processCommand(line, database);
            if (result != null && !result.isEmpty()) {
                outputWriter.write(result);
                outputWriter.newLine();
            }
        }

        System.out.println("Processing complete. Results written to: " + outputFilePath);


        database = null;


        reader.close();
        outputWriter.close();
    }
}
