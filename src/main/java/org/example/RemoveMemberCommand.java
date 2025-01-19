package org.example;

class RemoveMemberCommand implements Command{
        @Override
        public String execute(String[] parts, Database database) throws Exception {
            int museumCode = Integer.parseInt(parts[9].trim());
            String timetable = parts[10].trim();
            String name = parts[2].trim();
            String surname = parts[1].trim();

            Group group = CommandProcessor.findGroup(database, museumCode, timetable);
            if (group == null) {
                throw new GroupNotExistsException(String.format("%d ## %s ## GroupNotExistsException: Group does not exist for the specified museum code and timetable. ## (surname=%s, name=%s, role=%s, age=%s, email=%s, school=%s, additionalInfo=%s)",
                        museumCode, timetable, surname, name, parts[8], parts[4], parts[5], parts[6], parts[7]));
            }

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

}
