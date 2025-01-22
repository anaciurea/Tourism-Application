package org.example;

public class RemoveGuideCommand implements Command {
    public String execute(String[] parts, Database database) throws Exception {
        int museumCode = Integer.parseInt(parts[1].trim());
        String timetable = parts[2].trim();

        Group group = CommandProcessor.findGroup(database, museumCode, timetable);
        if (group == null) {
            throw new GroupNotExistsException(String.format(
                    "%d ## %s ## GroupNotExistsException: Group does not exist for the specified museum code and timetable.",
                    museumCode, timetable
            ));
        }

        Person removedGuide = group.getGuide();
        if (removedGuide == null) {
            throw new IllegalArgumentException(String.format(
                    "%d ## %s ## No guide exists for the specified group.",
                    museumCode, timetable
            ));
        }

        group.resetGuide();

        if (removedGuide instanceof Professor) {
            Professor professor = (Professor) removedGuide;
            return String.format(
                    "%d ## %s ## removed guide: surname=%s, name=%s, role=ghid, age=%d, email=%s, school=%s, experience=%d",
                    museumCode, timetable, professor.getSurname(), professor.getName(), professor.getRole(),
                    professor.getAge(), professor.getEmail(), professor.getSchool(), professor.getExperience()
            );
        } else {
            throw new IllegalArgumentException("The removed guide is not a professor.");
        }
    }
}