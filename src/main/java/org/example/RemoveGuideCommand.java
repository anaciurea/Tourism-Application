package org.example;

public class RemoveGuideCommand implements Command {
    @Override
    public String execute(String[] parts, Database database) throws Exception {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid REMOVE GUIDE command format");
        }

        int museumCode = Integer.parseInt(parts[1].trim());
        String timetable = parts[2].trim();

        Group group = CommandProcessor.findGroup(database, museumCode, timetable);
        if (group == null) {
            throw new GroupNotExistsException("Group does not exist.");
        }

        Person removedGuide = group.getGuide();
        group.resetGuide();

        return museumCode + " ## " + timetable + " ## removed guide: " + removedGuide;
    }
}