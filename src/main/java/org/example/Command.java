package org.example;

interface Command {
    String execute(String[] parts, Database database) throws Exception;
}
