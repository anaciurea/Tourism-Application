package org.example;

public class Event {
    private String organizerMessage;
    private long museumCode;
    private String message;

    public Event(long museumCode, String organizerMessage) {
        this.museumCode = museumCode;
        this.organizerMessage = organizerMessage;
    }

    public String getOrganizerMessage() {
        return organizerMessage;
    }

    public long getMuseumCode() {
        return museumCode;
    }

    public String toString() {
        return "Event at Museum " + museumCode;
    }
    public String getMessage() {
        return message;
    }
}
