package org.example;

public class GroupLogger implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Log: " + message);
    }
}
