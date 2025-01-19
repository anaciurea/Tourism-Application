package org.example;

public class AddMuseumCommand implements Command{
    public String execute(String[] parts, Database database) throws Exception {
        try {
            // Verificăm dacă există suficiente câmpuri
            if (parts.length < 5) {
                throw new IndexOutOfBoundsException("Insufficient data");
            }

            // Extragem și validăm codul muzeului
            long code = Long.parseLong(parts[1].trim());

            // Validăm numele muzeului
            String name = parts[2].trim();
            if (name.isEmpty()) {
                throw new NullPointerException("Missing museum name");
            }

            // Validăm locația (exemplu simplificat)
            String county = parts[3].trim();
            if (county.isEmpty()) {
                throw new NullPointerException("Missing county information");
            }

            // Creăm obiectul muzeu și îl adăugăm în baza de date
            Location location = new Location.Builder(county, 0).build();
            Museum museum = new Museum.Builder(name, code, 0, location).build();
            database.addMuseum(museum);

            return code + ": " + name;
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            // Dacă apare o eroare, aruncăm excepția pentru a fi captată în `Main`
            throw new IllegalArgumentException("Exception: Data is broken. ## (" + String.join("|", parts) + ")", e);
        }
    }
}
