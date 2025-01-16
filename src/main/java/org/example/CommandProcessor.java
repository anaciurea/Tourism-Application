package org.example;

public class CommandProcessor {
    public static String processCommand(String line, Database database) throws Exception {
        // Ignoră liniile goale sau care nu conțin comenzi valide
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(" ", 2);
        String command = parts[0];
        String params = parts.length > 1 ? parts[1] : "";

        switch (command.toUpperCase()) {
            case "ADD_MUSEUM":
                return addMuseum(params, database);
//            case "ADD_GROUP":
//                return addGroup(params, database);
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }
    }



    private static String addMuseum(String params, Database database) {
        try {
            // Parametrii separați de "|"
            String[] museumData = params.split("\\|");
            long code = Long.parseLong(museumData[1].trim());
            String name = museumData[2].trim();
            String county = museumData[3].trim();
            int sirutaCode = Integer.parseInt(museumData[15].trim());

            // Construim locația folosind builder-ul
            Location location = new Location.Builder(county, sirutaCode)
                    .setLocality(museumData[4].trim().isEmpty() ? null : museumData[4].trim())
                    .setAdminUnit(museumData[5].trim().isEmpty() ? null : museumData[5].trim())
                    .setAddress(museumData[6].trim().isEmpty() ? null : museumData[6].trim())
                    .setLatitude(museumData[19].trim().isEmpty() ? null : Integer.parseInt(museumData[19].trim().replace(",", "")))
                    .setLongitude(museumData[20].trim().isEmpty() ? null : Integer.parseInt(museumData[20].trim().replace(",", "")))
                    .build();

            // Construim muzeul folosind builder-ul
            Museum.Builder builder = new Museum.Builder(name, code, 0L, location);

            // Setăm câmpuri suplimentare, dacă există
            if (!museumData[10].isEmpty()) builder.setFoundingYear(Integer.parseInt(museumData[10].trim()));
            if (!museumData[8].isEmpty()) builder.setPhoneNumber(museumData[8].trim());
            if (!museumData[12].isEmpty()) builder.setManager(new Person(museumData[12].trim(), "", "director"));

            Museum museum = builder.build();
            database.addMuseum(museum);

            return code + ": " + name; // Rezultatul pentru testare sau debugging
        } catch (Exception e) {
            return "Exception: Data is broken. ## (" + params + ")";
        }
    }

}
