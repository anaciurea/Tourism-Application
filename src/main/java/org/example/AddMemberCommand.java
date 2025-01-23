package org.example;

class AddMemberCommand implements Command {
    public String execute(String[] parts, Database database) throws Exception {
        int museumCode = Integer.parseInt(parts[9].trim());
        String timetable = parts[10].trim();
        String name = parts[2].trim();
        String surname = parts[1].trim();
        int age = Integer.parseInt(parts[4].trim());
        String email = parts[5].isEmpty() ? "null" : parts[5].trim();
        String school = parts[6].trim();
        String role = "vizitator";
        String additionalInfo = parts[7].trim();


        System.out.println("Procesare: surname=" + surname + ", name=" + name + ", age=" + age + ", additionalInfo=" + additionalInfo);

        Group group = CommandProcessor.findOrCreateGroup(database, museumCode, timetable);

        if (group.getMembers().size() >= 10) {
            throw new GroupThresholdException(String.format(
                    "%d ## %s ## GroupThresholdException: Group cannot have more than 10 members. ## (new member: surname=%s, name=%s, role=%s, age=%d, email=%s, school=%s, %s)",
                    museumCode, timetable, surname, name, role, age, email, school,
                    parts[8].equals("student") ? "studyYear=" + additionalInfo : "experience=" + additionalInfo
            ));
        }

        Person member;
        String additionalField;


        if (parts[3].equals("student")) {
            int studyYear = Integer.parseInt(additionalInfo);
            Student student = new Student(surname, name, role);
            student.setAge(age);
            student.setEmail(email);
            student.setSchool(school);
            student.setStudyYear(studyYear);
            additionalField = "studyYear=" + studyYear;
            member = student;
        } else {
            int experience = Integer.parseInt(additionalInfo);
            Professor professor = new Professor(surname, name, role);
            professor.setAge(age);
            professor.setEmail(email);
            professor.setSchool(school);
            professor.setExperience(experience);
            additionalField = "experience=" + experience;
            member = professor;
        }

        group.addMember(member);

        return String.format(
                "%d ## %s ## new member: surname=%s, name=%s, role=%s, age=%d, email=%s, school=%s, %s",
                museumCode, timetable, surname, name, role, age, email, school, additionalField
        );
    }
}
