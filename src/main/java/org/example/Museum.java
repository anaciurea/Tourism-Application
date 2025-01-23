package org.example;

import java.util.ArrayList;
import java.util.List;

public class Museum {
    private String name;
    private long code;
    private long supervisorCode;
    private Location location;

    // Optionals
    private Person manager;
    private Integer foundingYear;
    private String phoneNumber;
    private String fax;
    private String email;
    private String url;
    private String profile;


    private List<Group> groups;

    private Museum(Builder builder) {
        this.name = builder.name;
        this.code = builder.code;
        this.supervisorCode = builder.supervisorCode;
        this.location = builder.location;

        this.manager = builder.manager;
        this.foundingYear = builder.foundingYear;
        this.phoneNumber = builder.phoneNumber;
        this.fax = builder.fax;
        this.email = builder.email;
        this.url = builder.url;
        this.profile = builder.profile;

        this.groups = new ArrayList<>();
    }


    public void addGroup(Group group) {
        groups.add(group);
    }


    public void notifyGuides(String organizerMessage) {
        for (Group group : groups) {
            Professor guide = group.getGuide();
            if (guide != null && guide.getEmail() != null) {
                System.out.printf(
                        "To: %s ## Message: %s (%d) %s%n",
                        guide.getEmail(), name, code, organizerMessage
                );
            }
        }
    }


    // Getteri existenți
    public String getName() {
        return name;
    }

    public long getCode() {
        return code;
    }

    public long getSupervisorCode() {
        return supervisorCode;
    }

    public Location getLocation() {
        return location;
    }

    public Person getManager() {
        return manager;
    }

    public Integer getFoundingYear() {
        return foundingYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public String getProfile() {
        return profile;
    }


    public static class Builder {
        private String name;
        private long code;
        private long supervisorCode;
        private Location location;

        private Person manager;
        private Integer foundingYear;
        private String phoneNumber;
        private String fax;
        private String email;
        private String url;
        private String profile;

        public Builder(String name, long code, long supervisorCode, Location location) {
            this.name = name;
            this.code = code;
            this.supervisorCode = supervisorCode;
            this.location = location;
        }

        public Builder setManager(Person manager) {
            this.manager = manager;
            return this;
        }

        public Builder setFoundingYear(Integer foundingYear) {
            this.foundingYear = foundingYear;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setFax(String fax) {
            this.fax = fax;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setProfile(String profile) {
            this.profile = profile;
            return this;
        }

        public Museum build() {
            return new Museum(this);
        }
    }

    @Override
    public String toString() {
        return code + ": " + name;
    }

    public boolean isValid() {
        return name != null && !name.isEmpty() &&
                location != null &&
                phoneNumber != null && !phoneNumber.isEmpty() &&
                email != null && !email.isEmpty() &&
                foundingYear != null;
    }

}
