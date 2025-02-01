package org.example;

public class AddMuseumCommand implements Command{
    public String execute(String[] parts, Database database) throws Exception {
        try {
            if (parts.length < 6)
                throw new IndexOutOfBoundsException("Insufficient data");

            long code = Long.parseLong(parts[1].trim());

            String name = parts[2].trim();
            if (name.isEmpty())
                throw new NullPointerException("Missing museum name");


            String county = parts[3].trim();
            if (county.isEmpty())
                throw new NullPointerException("Missing county information");

            Location location = new Location.Builder(county, 0).build();
            Museum museum = new Museum.Builder(name, code, 0, location).build();
            database.addMuseum(museum);

            return code + ": " + name;
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            throw new IllegalArgumentException("Exception: Data is broken. ## (" + String.join("|", parts) + ")", e);
        }
    }
}
