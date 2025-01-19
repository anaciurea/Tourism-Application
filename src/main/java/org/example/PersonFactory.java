package org.example;

public class PersonFactory {

    public static Person createPerson(String personData) {
        String[] data = personData.split("\\|");
        if (data.length < 3) {
            throw new IllegalArgumentException("Invalid person data: " + personData);
        }

        String surname = data[0].trim();
        String name = data[1].trim();
        String role = data[2].trim();

        int age = data.length > 3 && !data[3].trim().isEmpty() ? Integer.parseInt(data[3].trim()) : 0;
        String email = data.length > 4 ? data[4].trim() : "";
        String school = data.length > 5 ? data[5].trim() : "";
        int studyYear = data.length > 6 && !data[6].trim().isEmpty() ? Integer.parseInt(data[6].trim()) : 0;
        int experience = data.length > 7 && !data[7].trim().isEmpty() ? Integer.parseInt(data[7].trim()) : 0;

        return createPerson(surname, name, role, age, email, school, studyYear, experience);
    }


    public static Person createPerson(String surname, String name, String role, int age, String email, String school, int studyYear, int experience) {
        Person person;

        switch (role.toLowerCase()) {
            case "student":
                Student student = new Student(surname, name, role);
                student.setSchool(school);
                student.setStudyYear(studyYear);
                person = student;
                break;
            case "profesor":
                Professor professor = new Professor(surname, name, role);
                professor.setSchool(school);
                professor.setExperience(experience);
                person = professor;
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        person.setAge(age);
        person.setEmail(email);

        return person;
    }
}
