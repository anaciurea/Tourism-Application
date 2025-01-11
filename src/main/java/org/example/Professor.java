package org.example;

public class Professor extends Person {
    private int experience;
    private String school;

    public Professor(String surname, String name, String role) {
        super(surname, name, role);
    }

    public int getExperince() {
        return experience;
    }

    public String getSchool() {
        return school;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
