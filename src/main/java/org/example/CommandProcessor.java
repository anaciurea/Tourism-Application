package org.example;

public class CommandProcessor {
    public static void processCommand(PathTypes pathType, String command, Database database) {
        String[] parts = command.split(" ", 2);
        String action = parts[0];
        String params = parts.length > 1 ? parts[1] : "";

        try {
            switch (pathType) {
                case MUSEUMS:
                    if (action.equals("ADD") && params.startsWith("MUSEUM")) {
                        addMuseum(params.substring(7), database);
                    }
                    break;

//                case GROUPS:
//                    if (action.equals("ADD") && params.startsWith("GROUP")) {
//                        addGroup(params.substring(6), database);
//                    } else if (action.equals("ADD") && params.startsWith("GUIDE")) {
//                        addGuide(params.substring(6), database);
//                    } else if (action.equals("ADD") && params.startsWith("MEMBER")) {
//                        addMember(params.substring(7), database);
//                    } else if (action.equals("REMOVE") && params.startsWith("GUIDE")) {
//                        removeGuide(params.substring(7), database);
//                    }
//                    break;
//
//                case LISTENER:
//                    if (action.equals("ADD") && params.startsWith("EVENT")) {
//                        addEvent(params.substring(6), database);
//                    }
//                    break;

                default:
                    System.out.println("Unknown PathType: " + pathType);
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
    }

    private static void addMuseum(String params, Database database) {
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
                    .setLatitude(museumData[19].trim().isEmpty() ? null : Integer.parseInt(museumData[19].trim()))
                    .setLongitude(museumData[20].trim().isEmpty() ? null : Integer.parseInt(museumData[20].trim()))
                    .build();

            // Construim muzeul folosind builder-ul
            Museum.Builder builder = new Museum.Builder(name, code, 0L, location);

            // Setăm câmpuri suplimentare, dacă există
            if (!museumData[10].isEmpty()) builder.setFoundingYear(Integer.parseInt(museumData[10].trim()));
            if (!museumData[8].isEmpty()) builder.setPhoneNumber(museumData[8].trim());
            if (!museumData[12].isEmpty()) builder.setManager(new Person(museumData[12].trim(), "", "director"));

            Museum museum = builder.build();
            database.addMuseum(museum);

            System.out.println("Museum added: " + code + ": " + name);
        } catch (Exception e) {
            System.out.println("Exception: Data is broken. ## (" + params + ")");
        }
    }

}
