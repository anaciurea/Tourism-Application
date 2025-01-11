package org.example;

public class PersonFactory {

    public static Person createPerson(String surname, String name, String role, int age, String email, String school, int studyYear, int experience) {
        Person person;

        switch (role.toLowerCase()) {
            case "student" :
                Student student = new Student(surname, name, role);
                student.setSchool(school);
                student.setStudyYear(studyYear);
                person = student;
                break;
            case "professor" :
                Professor professor =  new Professor(surname, name, role);
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
