package org.example;

public class CommandProcessor {
    public static String processCommand(String line, Database database) throws Exception {
        // Verificăm dacă linia este validă
        try {
            if (line == null || line.trim().isEmpty()) {
                return null;
            }

            // Împărțim linia în părți folosind delimitatorul "|"
            String[] parts = line.split("\\|");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid command format: " + line);
            }

            String command = parts[0].trim().toUpperCase();
            switch (command) {
                case "ADD MUSEUM":
                    return addMuseum(parts, database);
                case "ADD GUIDE":
                    return addGuide(parts, database);
                case "FIND GUIDE":
                    return findGuide(parts, database);
                case "REMOVE GUIDE":
                    return removeGuide(parts, database);
                case "ADD MEMBER":
                    return addMember(parts, database);
                case "FIND MEMBER":
                    return findMember(parts, database);
                case "REMOVE MEMBER":
                    return removeMember(parts, database);

                default:
                    throw new IllegalArgumentException("Unknown command: " + command);
            }
        } catch (GroupThresholdException | PersonNotExistsException | GroupNotExistsException e) {
            return e.getMessage();
        } catch (Exception e) {
            return String.format("Exception: An error occurred while processing the command. ## (%s)", line);
        }
    }

    private static String addMuseum(String[] parts, Database database) {
        try {
            // Verificăm dacă există suficiente câmpuri
            if (parts.length < 5) {
                throw new IndexOutOfBoundsException("Insufficient data");
            }

            // Extragem și validăm codul muzeului
            long code = Long.parseLong(parts[1].trim());

            // Validăm numele muzeului
            String name = parts[2].trim();
            if (name.isEmpty()) {
                throw new NullPointerException("Missing museum name");
            }

            // Validăm locația (exemplu simplificat)
            String county = parts[3].trim();
            if (county.isEmpty()) {
                throw new NullPointerException("Missing county information");
            }

            // Creăm obiectul muzeu și îl adăugăm în baza de date
            Location location = new Location.Builder(county, 0).build();
            Museum museum = new Museum.Builder(name, code, 0, location).build();
            database.addMuseum(museum);

            return code + ": " + name;
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            // Dacă apare o eroare, aruncăm excepția pentru a fi captată în `Main`
            throw new IllegalArgumentException("Exception: Data is broken. ## (" + String.join("|", parts) + ")", e);
        }
    }

    private static String addGuide(String[] parts, Database database) throws GuideExistsException, GuideTypeException {
        int museumCode = Integer.parseInt(parts[9].trim());
        String timetable = parts[10].trim();
        String name = parts[2].trim();
        String surname = parts[1].trim();
        int age = Integer.parseInt(parts[4].trim());
        String email = parts[5].trim();
        String school = parts[6].trim();
        int experience = Integer.parseInt(parts[7].trim());

        Group group = findOrCreateGroup(database, museumCode, timetable);
        Professor guide = new Professor(surname, name, "professor");

        // Set optional fields
        guide.setAge(age);
        guide.setEmail(email);
        guide.setSchool(school);
        guide.setExperience(experience);

        group.addGuide(guide);

        return String.format("%d ## %s ## new guide: surname=%s, name=%s, role=ghid, age=%d, email=%s, school=%s, experience=%d",
                museumCode, timetable, surname, name, age, email, school, experience);
    }

    private static String findGuide(String[] parts, Database database) throws GroupNotExistsException {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid FIND GUIDE command format");
        }

        int museumCode = Integer.parseInt(parts[1].trim());
        String timetable = parts[2].trim();
        String personDetails = parts[3].trim();

        Group group = findGroup(database, museumCode, timetable);
        if (group == null) {
            throw new GroupNotExistsException("Group does not exist.");
        }

        Person guide = group.getGuide();
        if (guide != null && guide.toString().equals(personDetails)) {
            return String.format("%d ## %s ## guide found: %s", museumCode, timetable, personDetails);
        } else {
            return String.format("%d ## %s ## guide not exists: %s", museumCode, timetable, personDetails);
        }
    }

    private static String removeGuide(String[] parts, Database database) throws GroupNotExistsException {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid REMOVE GUIDE command format");
        }

        int museumCode = Integer.parseInt(parts[1].trim());
        String timetable = parts[2].trim();

        Group group = findGroup(database, museumCode, timetable);
        if (group == null) {
            throw new GroupNotExistsException("Group does not exist.");
        }

        Person removedGuide = group.getGuide();
        group.resetGuide();

        return museumCode + " ## " + timetable + " ## removed guide: " + removedGuide;
    }



    private static String findMember(String[] parts, Database database) throws GroupNotExistsException {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid FIND MEMBER command format");
        }

        int museumCode = Integer.parseInt(parts[1].trim());
        String timetable = parts[2].trim();
        String personDetails = parts[3].trim();

        Group group = findGroup(database, museumCode, timetable);
        Person member = PersonFactory.createPerson(personDetails);

        if (group == null) {
            throw new GroupNotExistsException("Group does not exist.");
        }

        if (group.getMembers().contains(member)) {
            return museumCode + " ## " + timetable + " member found: " + member;
        } else {
            return museumCode + " ## " + timetable + " member not exists: " + member;
        }
    }

    private static String removeMember(String[] parts, Database database) throws PersonNotExistsException, GroupNotExistsException {
        int museumCode = Integer.parseInt(parts[9].trim());
        String timetable = parts[10].trim();
        String name = parts[2].trim();
        String surname = parts[1].trim();

        Group group = findGroup(database, museumCode, timetable);
        if (group == null) {
            throw new GroupNotExistsException(String.format("%d ## %s ## GroupNotExistsException: Group does not exist. ## (surname=%s, name=%s, role=%s, age=%s, email=%s, school=%s, additionalInfo=%s)",
                    museumCode, timetable, surname, name, parts[8], parts[4], parts[5], parts[6], parts[7]));
        }

        // Find the member with detailed attribute matching
        Person memberToRemove = group.getMembers().stream()
                .filter(m -> m.getName().equals(name) &&
                        m.getSurname().equals(surname) &&
                        ((m instanceof Student && Integer.toString(((Student) m).getStudyYear()).equals(parts[7].trim())) ||
                                (m instanceof Professor && Integer.toString(((Professor) m).getExperience()).equals(parts[7].trim()))))
                .findFirst()
                .orElseThrow(() -> new PersonNotExistsException(String.format("%d ## %s ## PersonNotExistsException: Person was not found in the group. ## (surname=%s, name=%s, role=%s, age=%s, email=%s, school=%s, additionalInfo=%s)",
                        museumCode, timetable, surname, name, parts[8], parts[4], parts[5], parts[6], parts[7])));

        group.removeMember(memberToRemove);

        String additionalField = "";
        if (memberToRemove instanceof Student) {
            additionalField = "school=" + ((Student) memberToRemove).getSchool() + ", studyYear=" + ((Student) memberToRemove).getStudyYear();
        } else if (memberToRemove instanceof Professor) {
            additionalField = String.format("school=%s, experience=%d",
                    ((Professor) memberToRemove).getSchool(),
                    ((Professor) memberToRemove).getExperience());
        }

        return String.format("%d ## %s ## removed member: surname=%s, name=%s, role=%s, age=%d, email=%s, %s",
                museumCode, timetable, surname, name, memberToRemove.getRole(), memberToRemove.getAge(),
                memberToRemove.getEmail() != null ? memberToRemove.getEmail() : "null",
                additionalField);
    }

    // Updated addMember method to ensure correct attribute handling and group limit validation
    private static String addMember(String[] parts, Database database) throws GroupThresholdException {
        int museumCode = Integer.parseInt(parts[9].trim());
        String timetable = parts[10].trim();
        String name = parts[2].trim();
        String surname = parts[1].trim();
        int age = Integer.parseInt(parts[4].trim());
        String email = parts[5].isEmpty() ? "null" : parts[5].trim();
        String school = parts[6].trim();
        String role = parts[8].trim();
        String additionalInfo = parts[7].trim();

        Group group = findOrCreateGroup(database, museumCode, timetable);

        if (group.getMembers().size() >= 10) {
            throw new GroupThresholdException(String.format("%d ## %s ## GroupThresholdException: Group cannot have more than 10 members. ## (new member: surname=%s, name=%s, role=%s, age=%d, email=%s, school=%s, additionalInfo=%s)",
                    museumCode, timetable, surname, name, role, age, email, school, additionalInfo));
        }

        Person member;
        String additionalField;
        if (role.equalsIgnoreCase("student")) {
            int studyYear = Integer.parseInt(additionalInfo);
            Student student = new Student(surname, name, "student");
            student.setAge(age);
            student.setEmail(email);
            student.setSchool(school);
            student.setStudyYear(studyYear);
            member = student;
            additionalField = "studyYear=" + studyYear;
        } else {
            int experience = Integer.parseInt(additionalInfo);
            Professor professor = new Professor(surname, name, "professor");
            professor.setAge(age);
            professor.setEmail(email);
            professor.setSchool(school);
            professor.setExperience(experience);
            member = professor;
            additionalField = "experience=" + experience;
        }

        group.addMember(member);

        return String.format("%d ## %s ## new member: surname=%s, name=%s, role=%s, age=%d, email=%s, school=%s, %s",
                museumCode, timetable, surname, name, role, age, email, school, additionalField);
    }

    private static Group findGroup(Database database, int museumCode, String timetable) {
        return database.getGroups().stream()
                .filter(g -> g.getMuseumCode() == museumCode && g.getTimetable().equals(timetable))
                .findFirst()
                .orElse(null);
    }

    private static Group findOrCreateGroup(Database database, int museumCode, String timetable) {
        Group group = database.getGroups().stream()
                .filter(g -> g.getMuseumCode() == museumCode && g.getTimetable().equals(timetable))
                .findFirst()
                .orElse(null);

        if (group == null) {
            group = new Group(museumCode, timetable);
            database.addGroup(group);
        }

        return group;
    }
}