package org.example;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Person> members;
    private Professor guide;
    private Integer museumCode;
    private String timetable;


    public Group(Integer museumCode, String timetable) {
        this.members = new ArrayList<>();
        this.museumCode = museumCode;
        this.timetable = timetable;
    }


    // Metodă pentru adăugarea ghidului
    public void addGuide(Person guide) throws GuideExistsException, GuideTypeException {
        if (this.guide != null) {
            throw new GuideExistsException();
        }
        if (!(guide instanceof Professor)) {
            throw new GuideTypeException();
        }
        this.guide = (Professor) guide;
    }

    // Metodă pentru ștergerea ghidului
    public void resetGuide() {
        this.guide = null;
    }

    // Metodă pentru adăugarea unui membru
    public void addMember(Person member) throws GroupThresholdException {
        if (members.size() >= 10) {
            throw new GroupThresholdException();
        }
        members.add(member);
    }

    // Metodă pentru eliminarea unui membru
    public void removeMember(Person member) throws PersonNotExistsException {
        if (!members.remove(member)) {
            throw new PersonNotExistsException();
        }
    }


    public List<Person> getMembers() {
        return members;
    }

    public Professor getGuide() {
        return guide;
    }

    public Integer getMuseumCode() {
        return museumCode;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public void setGuide(Professor guide) {
        this.guide = guide;
    }

    public void setMuseumCode(Integer museumCode) {
        this.museumCode = museumCode;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }
}
