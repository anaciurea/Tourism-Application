
package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Proiect 2025");

        StringBuilder inputFilePath = new StringBuilder(args[1]);
        inputFilePath.append(".in");
        StringBuilder outputFilePath = new StringBuilder(args[1]);
        outputFilePath.append(".out");

        File inputFile = new File(inputFilePath.toString());
        File outputFile = new File(outputFilePath.toString());
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.err.println("Error: Input file does not exist: " + inputFilePath);
            return;
        }


        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath.toString()));
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFilePath.toString()));

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

        reader.close();
        outputWriter.close();
    }
}
