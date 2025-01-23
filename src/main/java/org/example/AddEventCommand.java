package org.example;

public class AddEventCommand implements Command {
    @Override
    public String execute(String[] parts, Database database) throws Exception {

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid ADD EVENT command format.");
        }


        String museumCode = parts[1].trim();
        String organizerMessage = parts[2].trim();


        Museum museum = database.getMuseumByCode(Long.parseLong(museumCode));
        if (museum == null) {
            throw new IllegalArgumentException("Museum not found for code: " + museumCode);
        }


        museum.notifyGuides(organizerMessage);

        return String.format("Event added for museum %s (%s): %s", museum.getName(), museumCode, organizerMessage);
    }
}
