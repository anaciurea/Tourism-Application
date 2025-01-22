package org.example;

public class AddGuideCommand implements Command {
    public String execute(String[] parts, Database database) throws Exception {
        int museumCode = Integer.parseInt(parts[9].trim());
        String timetable = parts[10].trim();
        String name = parts[2].trim();
        String surname = parts[1].trim();
        int age = Integer.parseInt(parts[4].trim());
        String email = parts[5].trim();
        String school = parts[6].trim();
        int experience = Integer.parseInt(parts[7].trim());

        Group group = CommandProcessor.findOrCreateGroup(database, museumCode, timetable);

        if (group.getGuide() != null) {
            throw new GuideExistsException(String.format(
                    "%d ## %s ## GuideExistsException: Guide already exists. ## (new guide: surname=%s, name=%s, role=ghid, age=%d, email=%s, school=%s, experience=%d)",
                    museumCode, timetable, surname, name, age, email, school, experience
            ));
        }

        Professor guide = new Professor(surname, name, "professor");
        guide.setAge(age);
        guide.setEmail(email);
        guide.setSchool(school);
        guide.setExperience(experience);

        group.addGuide(guide);

        return String.format(
                "%d ## %s ## new guide: surname=%s, name=%s, role=ghid, age=%d, email=%s, school=%s, experience=%d",
                museumCode, timetable, surname, name, age, email, school, experience
        );
    }
}