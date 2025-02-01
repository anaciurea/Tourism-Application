package org.example;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import org.example.Event;



public class Database {
    private static Database instance;

    private Set<Museum> museums;
    private Set<Group> groups;
    private List<Event> events = new ArrayList<>();


    private Database() {
        this.museums = new HashSet<>();
        this.groups = new HashSet<>();
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    //getters
    public Set<Museum> getMuseums() {
        return museums;
    }

    public Set<Group> getGroups() {
        return groups;
    }
    public void addMuseum(Museum museum) {
        museums.add(museum);
    }

    public void addMuseums(Set<Museum> museums) {
        this.museums.addAll(museums);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public  void addGroups(Set<Group> groups) {
        this.groups.addAll(groups);
    }

    public String getAllMuseums() {
        StringBuilder sb = new StringBuilder();
        for (Museum museum : museums) {
            sb.append(museum.toString()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    public Museum getMuseumByCode(long code) {
        for (Museum museum : museums) {
            if (museum.getCode() == code) {
                return museum;
            }
        }
        return null;
    }

    public void addEvent(long museumCode, String message) {
        Museum museum = getMuseumByCode(museumCode);
        if (museum != null) {
            museum.notifyGuides(message);
        } else {
            System.out.println("Error: Museum with code " + museumCode + " not found.");
        }
    }

}
