package org.example;

public class AddGuideCommand implements Command {
    public String execute(String[] parts, Database database) throws Exception {
        int museumCode;
        String timetable;
        String name;
        String surname;
        int age;
        String email;
        String school;
        int experience;

        try {
            museumCode = Integer.parseInt(parts[9].trim());
            timetable = parts[10].trim();
            name = parts[2].trim();
            surname = parts[1].trim();
            age = Integer.parseInt(parts[4].trim());
            email = parts[5].trim();
            school = parts[6].trim();
            experience = Integer.parseInt(parts[7].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input format detected.");
        }

        Group group = CommandProcessor.findOrCreateGroup(database, museumCode, timetable);

        if (parts[3].equals("profesor")) {
            if (group.getGuide() != null) {
                throw new GuideExistsException(String.format(
                        "%d ## %s ## GuideExistsException: Guide already exists. ## (new guide: surname=%s, name=%s, role=ghid, age=%d, email=%s, school=%s, experience=%d)",
                        museumCode, timetable, group.getGuide().getSurname(), group.getGuide().getName(), group.getGuide().getAge(), group.getGuide().getEmail(), group.getGuide().getSchool(), group.getGuide().getExperience()
                ));
            }
            Professor guide = new Professor(surname, name, "profesor");
            guide.setAge(age);
            guide.setEmail(email);
            guide.setSchool(school);
            guide.setExperience(experience);

            group.addGuide(guide);
            System.out.println("Am adaugat profesorul in grupa" + group.getTimetable() + " " + group.getMuseumCode() + " " + group.getGuide().getName() + "\n");
        }

        else {
            throw new GuideTypeException(String.format(
                    "%d ## %s ## GuideTypeException: Guide must be a professor. ## (new guide: surname=%s, name=%s, role=vizitator, age=%d, email=%s, school=%s, studyYear=%d)",
                    museumCode, timetable, surname, name, age, email, school, experience
            ));
        }

        System.out.println(group.guideExists());

        return String.format(
                "%d ## %s ## new guide: surname=%s, name=%s, role=ghid, age=%d, email=%s, school=%s, experience=%d",
                museumCode, timetable, surname, name, age, email, school, experience
        );
    }
}