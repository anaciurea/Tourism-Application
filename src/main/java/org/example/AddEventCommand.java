package org.example;

public class AddEventCommand implements Command {
    @Override
    public String execute(String[] parts, Database database) throws Exception {
        // Verificăm formatul comenzii
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid ADD EVENT command format.");
        }

        // Extragem codul muzeului și mesajul organizatorului din parametrii comenzii
        String museumCode = parts[1].trim();
        String organizerMessage = parts[2].trim();

        // Găsim muzeul pe baza codului
        Museum museum = database.getMuseumByCode(Long.parseLong(museumCode));
        if (museum == null) {
            throw new IllegalArgumentException("Museum not found for code: " + museumCode);
        }

        // Notificăm ghizii despre eveniment
        museum.notifyGuides(organizerMessage);

        // Returnăm un mesaj de confirmare
        return String.format("Event added for museum %s (%s): %s", museum.getName(), museumCode, organizerMessage);
    }
}
