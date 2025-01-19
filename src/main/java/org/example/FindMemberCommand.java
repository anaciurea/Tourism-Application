package org.example;

 class FindMemberCommand implements Command{
    public String execute(String[] parts, Database database) throws Exception {
        return CommandProcessor.findMember(parts, database);
    }
}
