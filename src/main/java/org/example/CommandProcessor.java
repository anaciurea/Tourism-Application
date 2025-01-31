package org.example;

public class CommandProcessor {
    public static String processCommand(String line, Database database) {
        try {
            if (line == null || line.trim().isEmpty()) {
                throw new IllegalArgumentException("Empty or null command line.");
            }

            String[] parts = line.split("\\|");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid command format: " + line);
            }

            String commandType = parts[0].trim().toUpperCase();
            Command command;

            switch (commandType) {
                case "ADD MUSEUM":
                    command = new AddMuseumCommand();
                    break;
                case "ADD MEMBER":
                    command = new AddMemberCommand();
                    break;
                case "REMOVE MEMBER":
                    command = new RemoveMemberCommand();
                    break;
                case "FIND MEMBER":
                    command = new FindMemberCommand();
                    break;
                case "ADD GUIDE":
                    command = new AddGuideCommand();
                    break;
                case "REMOVE GUIDE":
                    command = new RemoveGuideCommand();
                    break;
                case "FIND GUIDE":
                    command = new FindGuideCommand();
                    break;
                case "ADD EVENT":
                    command = new AddEventCommand();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command: " + commandType);
            }

            return command.execute(parts, database);

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static Group findOrCreateGroup(Database database, int museumCode, String timetable) {
        Group group = database.getGroups().stream()
                .filter(g -> g.getMuseumCode() == museumCode && g.getTimetable().equals(timetable))
                .findFirst()
                .orElse(null);

        if (group == null) {
            group = new Group(museumCode, timetable);
            database.addGroup(group);
        }

        if (group.getObservers().isEmpty()) {
//            group.addObserver(new GroupLogger());
        }

        return group;
    }


    public static Group findGroup(Database database, int museumCode, String timetable) throws GroupNotExistsException {
        return database.getGroups().stream()
                .filter(g -> g.getMuseumCode() == museumCode && g.getTimetable().equals(timetable))
                .findFirst()
                .orElseThrow(() -> new GroupNotExistsException(String.format(
                        "%d ## %s ## GroupNotExistsException: Group does not exist.",
                        museumCode, timetable
                )));
    }



    public static String findGuide(String[] parts, Database database) throws GroupNotExistsException {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid FIND GUIDE command format");
        }

        int museumCode = Integer.parseInt(parts[1].trim());
        String timetable = parts[2].trim();
        String personDetails = parts[3].trim();

        Group group = findGroup(database, museumCode, timetable);
        if (group == null) {

        }

        Person guide = group.getGuide();
        if (guide != null && guide.toString().equals(personDetails)) {
            return String.format("%d ## %s ## guide found: %s", museumCode, timetable, personDetails);
        } else {
            return String.format("%d ## %s ## guide not exists: %s", museumCode, timetable, personDetails);
        }
    }

    public static String findMember(String[] parts, Database database) throws GroupNotExistsException {
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

}
