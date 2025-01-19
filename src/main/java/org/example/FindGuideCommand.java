package org.example;

public class FindGuideCommand implements Command {
    @Override
    public String execute(String[] parts, Database database) throws Exception {
        return CommandProcessor.findGuide(parts, database);
    }
}
