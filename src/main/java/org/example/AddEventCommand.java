package org.example;

import java.util.List;

class AddEventCommand implements Command {
    public String execute(String[] parts, Database database) throws Exception {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid ADD EVENT command format");
        }

        int museumCode = Integer.parseInt(parts[1].trim());
        String eventMessage = parts[2].trim();

        Museum museum = database.getMuseums().stream()
                .filter(m -> m.getCode() == museumCode)
                .findFirst()
                .orElse(null);

        if (museum == null) {
            throw new IllegalArgumentException("Museum with code " + museumCode + " does not exist.");
        }

        // Notify all guides associated with this museum
        museum.notifyGuides(eventMessage);

        return String.format("Event added: %s (%d) %s", museum.getName(), museumCode, eventMessage);
    }
}
