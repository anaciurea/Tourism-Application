package org.example;

public class RemoveGuideCommand implements Command {
    public String execute(String[] parts, Database database) throws Exception {
        Integer museumCode;
        String timetable;

        try {
            if (parts.length < 3) {
                return "error: Insufficient input data.";
            }

            museumCode = Integer.parseInt(parts[9].trim());
            timetable = parts[10].trim();
        } catch (NumberFormatException e) {
            return String.format("error: Invalid input format for museumCode: %s.", parts[9]);
        } catch (Exception e) {
            return "error: Unexpected input error.";
        }

        Group group = CommandProcessor.findGroup(database, museumCode, timetable, parts);
        if (group == null) {
            return String.format("%d ## %s ## error: Group does not exist.", museumCode, timetable);
        }


        Professor removedGuide = group.getGuide();
        if (removedGuide == null) {
            return String.format("%d ## %s ## error: No guide to remove.", museumCode, timetable);
        }

        try {
            group.resetGuide();
        } catch (GuideExistsException e) {
            return String.format("%d ## %s ## GuideTypeException: Guide cannot be removed due to type restrictions.", museumCode, timetable);
        }

            return String.format(
                    "%d ## %s ## removed guide: surname=%s, name=%s, role=ghid, age=%d, email=%s, school=%s, experience=%d",
                    museumCode, timetable, removedGuide.getSurname(), removedGuide.getName(),
                    removedGuide.getAge(), removedGuide.getEmail(), removedGuide.getSchool(), removedGuide.getExperience());

    }
}
