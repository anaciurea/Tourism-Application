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

    
    public void addGuide(Person guide) throws GuideExistsException, GuideTypeException {
        if (this.guide != null) {
            throw new GuideExistsException("A guide already exists for this group.");
        }
        if (!(guide instanceof Professor)) {
           throw new GuideTypeException("Guide must be a professor.");

        }
        this.guide = (Professor) guide;
    }

    public void resetGuide() {
        this.guide = null;
    }

    public void addMember(Person member) throws GroupThresholdException {
        if (members.size() >= 10) {
            throw new GroupThresholdException("The group threshold has been exceeded.");
        }
        members.add(member);
    }

    public void removeMember(Person member) throws PersonNotExistsException {
        if (!members.remove(member)) {
            throw new PersonNotExistsException("Person was not found in the group.");
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
