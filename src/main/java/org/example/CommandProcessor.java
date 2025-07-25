
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
               group.addObserver(new GroupLogger());
        }

        return group;
    }


    public static Group findGroup(Database database, int museumCode, String timetable, String parts[]) {
        Group gr = null;

        for(Group g : database.getGroups())
            if(g.getTimetable().equals(timetable) && g.getMuseumCode().equals(museumCode)){
                gr = g;
                break;
            }

        return gr;
    }


    public static String findGuide(String[] parts, Database database) throws GroupNotExistsException {
        if (parts.length < 11) {
            throw new IllegalArgumentException("Invalid FIND GUIDE command format");
        }

        int museumCode = Integer.parseInt(parts[9].trim());
        String timetable = parts[10].trim();

        Group group = findGroup(database, museumCode, timetable, parts);
        if (group == null) {
            throw new GroupNotExistsException("Group does not exist.");
        }

        if (group.getGuide() != null) {
            if (group.getGuide().getSurname().equalsIgnoreCase(parts[1]) &&
                    group.getGuide().getName().equalsIgnoreCase(parts[2]) &&
                    parts[8].equalsIgnoreCase("ghid")) {
                return museumCode + " ## " + timetable + " ## guide found: surname=" + parts[1] + ", name=" + parts[2] + ", role=ghid, age=" + parts[4] + ", email=" + parts[5] + ", school=" + parts[6] + ", experience=" + parts[7];
            }
        }

        return museumCode + " ## " + timetable + " ## guide not exists: surname=" + parts[1] + ", name=" + parts[2] + ", role=" + parts[8] + ", age=" + parts[4] + ", email=" + parts[5]+ ", school=" + parts[6] + ", experience=" + parts[7];
    }


    public static String findMember(String[] parts, Database database) throws GroupNotExistsException {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid FIND MEMBER command format");
        }

        int museumCode = Integer.parseInt(parts[9].trim());
        String timetable = parts[10].trim();
        String personDetails = parts[3].trim();
        String email = parts[5];

        Group group = findGroup(database, museumCode, timetable, parts);
        if (group == null) {
            throw new GroupNotExistsException("Group does not exist.");
        }

        System.out.println(group.findMember(parts[2]));
        if(group.findMember(parts[2]) && group.findMemberBySurname(parts[1])) {
            return museumCode + " ## " + timetable + " ## member found: surname=" + parts[1] + ", name=" + parts[2] + ", role=" + parts[8] + ", age=" + parts[4] + ", email=" + parts[5] + ", school=" + parts[6] + ", experience=" + parts[7];
        } else {
            return museumCode + " ## " + timetable + " ## member not exists: surname=" + parts[1] + ", name=" + parts[2] + ", role=" + parts[8] + ", age=" + parts[4] + ", email=" + parts[5]+ ", school=" + parts[6] + ", experience=" + parts[7];         }
    }

}
