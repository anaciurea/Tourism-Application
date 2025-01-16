package org.example;

public class CommandProcessor {
    public static String processCommand(String line, Database database) throws Exception {
        // Verificăm dacă linia este validă
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        // Împărțim linia în părți folosind delimitatorul "|"
        String[] parts = line.split("\\|");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid command format: " + line);
        }

        String command = parts[0].trim().toUpperCase(); // Primul element este comanda
        switch (command) {
            case "ADD MUSEUM":
                return addMuseum(parts, database);
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }
    }

    private static String addMuseum(String[] parts, Database database) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid museum data: " + String.join("|", parts));
        }

        try {
            // Extragem datele muzeului din linie
            long code = Long.parseLong(parts[1].trim());
            String name = parts[2].trim();
            String county = parts[3].trim();
            String locality = parts[4].trim();

            // Creăm obiectul Location și Museum
            Location location = new Location.Builder(county, 0).build();
            Museum museum = new Museum.Builder(name, code, 0, location).build();

            // Adăugăm muzeul în baza de date
            database.addMuseum(museum);

            // Returnăm mesajul de succes
            return code + ": " + name;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid museum code format: " + parts[1].trim());
        }
    }
}
