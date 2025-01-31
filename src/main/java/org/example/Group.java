package org.example;

import java.util.ArrayList;
import java.util.List;


public class Group {
    private List<Person> members;
    private Professor guide = null;
    private Integer museumCode;
    private String timetable;

    private List<Observer> observers;

    public Group(Integer museumCode, String timetable) {
        this.members = new ArrayList<>();
        this.museumCode = museumCode;
        this.timetable = timetable;
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void addGuide(Professor guide) throws GuideExistsException {
        if (this.guide != null) {
            throw new GuideExistsException("A guide already exists for this group.");
        }
//        if (!(guide instanceof Professor)) {
//            throw new GuideTypeException("Guide must be a professor.");
//        }
        this.guide = guide;
        notifyObservers("Guide added: " + guide.getName());
    }

//    public void resetGuide() throws  GuideExistsException{
//        notifyObservers("Guide removed: " + (this.guide != null ? this.guide.getName() : "None"));
//        this.guide = null;
//    }
public void resetGuide() throws GuideExistsException{
    if(guideExists())
        guide = null;
    else
        throw new GuideExistsException("## GuideExistsException: Guide already exists. ##");
//    boolean mustHaveGuide = true; // Schimbă regula după nevoie
//    if (mustHaveGuide) {
//        throw new GuideExistsException("Error: Guide cannot be removed without a replacement.");
//    }
//
//    this.guide = null; // Eliminăm ghidul doar dacă regula permite
}



    public void addMember(Person member) throws GroupThresholdException {
        if (members.size() >= 10) {
            throw new GroupThresholdException("The group threshold has been exceeded.");
        }
        members.add(member);
        notifyObservers("Member added: " + member.getName());
    }

    public void removeMember(Person member) throws PersonNotExistsException {
        if (!members.remove(member)) {
            throw new PersonNotExistsException("Person was not found in the group.");
        }
        notifyObservers("Member removed: " + member.getName());
    }

    public List<Person> getMembers() {
        return members;
    }

    public Professor getGuide() {
        return guide;
    }
    public boolean guideExists(){
        if(guide != null)
            return true;
        else
            return false;
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

    public List<Observer> getObservers() {
        return observers;
    }

    public boolean findMember(String name) {
        for(Person person : members) {
            if(person.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
