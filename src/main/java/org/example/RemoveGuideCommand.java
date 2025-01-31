package org.example;

public class RemoveGuideCommand implements Command {
    public String execute(String[] parts, Database database) throws Exception {
        Integer museumCode;
        String timetable;
        System.out.println("DEBUG: Processing command -> " + parts[0]);

        try {
            if (parts.length < 3) {
                return "error: Insufficient input data.";
            }

            museumCode = Integer.parseInt(parts[9].trim());
            timetable = parts[10].trim();
            System.out.println(museumCode + "codul muzeului");
        } catch (NumberFormatException e) {
            return String.format("error: Invalid input format for museumCode: %s.", parts[9]);
        } catch (Exception e) {
            return "error: Unexpected input error.";
        }

        Group group = CommandProcessor.findGroup(database, museumCode, timetable);
        if (group == null) {
            return String.format("%d ## %s ## error: Group does not exist.", museumCode, timetable);
        }


        Person removedGuide = group.getGuide();
        if (removedGuide == null) {
            return String.format("%d ## %s ## error: No guide to remove.", museumCode, timetable);
        }

//        try {
            group.resetGuide();
//        } catch (GuideExistsException e) {
//            return e.getMessage();
//        }

        if (removedGuide instanceof Professor) {
            Professor professor = (Professor) removedGuide;

            return String.format(
                    "%d ## %s ## success: removed guide: surname=%s, name=%s, role=ghid, age=%d, email=%s, school=%s, experience=%d",
                    museumCode, timetable, professor.getSurname(), professor.getName(), professor.getRole(),
                    professor.getAge(), professor.getEmail(), professor.getSchool(), professor.getExperience()
            );
        } else {
            return String.format("%d ## %s ## error: Removed guide is not a valid professor.", museumCode, timetable);
        }
    }
}
