package org.example;

public class PersonFactory {

    public static Person createPerson(String surname, String name, String role, int age, String email, String school, int specificValue) {
        Person person;

        switch (role.toLowerCase()) {
            case "vizitator":
                Student student = new Student(surname, name, role);
                student.setSchool(school);
                student.setStudyYear(specificValue);
                person = student;
                break;
            case "ghid":
                Professor professor = new Professor(surname, name, role);
                professor.setSchool(school);
                professor.setExperience(specificValue);
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
