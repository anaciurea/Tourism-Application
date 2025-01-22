package org.example;

public class Event {
    private String organizerMessage;
    private String museumCode;

    public Event(String museumCode, String organizerMessage) {
        this.museumCode = museumCode;
        this.organizerMessage = organizerMessage;
    }

    public String getOrganizerMessage() {
        return organizerMessage;
    }

    public String getMuseumCode() {
        return museumCode;
    }
}
