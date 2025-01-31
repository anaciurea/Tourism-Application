package org.example;

public class AddEventCommand implements Command {
    @Override
    public String execute(String[] parts, Database database) throws Exception {
        if (parts.length < 3) {
            return "Error: Invalid ADD EVENT command format.";
        }

        try {
            long museumCode = Long.parseLong(parts[1].trim());
            String organizerMessage = parts[2].trim();

            Museum museum = database.getMuseumByCode(museumCode);
            if (museum == null) {
                return "Error: Museum not found for code " + museumCode;
            }

            // Trimite notificări ghizilor muzeului
            museum.notifyGuides(organizerMessage);

            return String.format("Event added for museum %s (%d): %s", museum.getName(), museumCode, organizerMessage);
        } catch (NumberFormatException e) {
            return "Error: Invalid museum code format.";
        }
    }
}
