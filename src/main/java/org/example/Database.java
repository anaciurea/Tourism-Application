package org.example;
import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.Set;

public class Database {
    private static Database instance;

    private Set<Museum> museums;
    private Set<Group> groups;

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
}
