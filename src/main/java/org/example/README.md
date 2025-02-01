 Ciurea Ana - Sorina 321 CC
# POO Project 2025 - Tourism Application

## Project Description
This application manages the database of museums and the organization of tourist groups.
It is responsible for adding, searching, and managing guides, visitors, and museums efficiently.

## 📐 Design Patterns Used
For implementing this project, we have used the following design patterns:

### 1️⃣ **Singleton** - `Database.java`
**Motivation:**
- Prevents multiple instances of the database, ensuring a single source of truth for museums and tourist groups.
- Implementation:
  ```java
  public class Database {
      private static Database instance;
      private Set<Museum> museums;
      private Set<Group> groups;
      
      private Database() {
          museums = new HashSet<>();
          groups = new HashSet<>();
      }
      
      public static Database getInstance() {
          if (instance == null) {
              instance = new Database();
          }
          return instance;
      }
  }
  ```

### 2️⃣ **Builder** - `Location.java` & `Museum.java`
**Motivation:**
- Enables the flexible and fluent construction of `Location` and `Museum` objects, ensuring readability and maintainability.
- Implementation:
  ```java
  public class Location {
      private String county;
      private Integer sirutaCode;
      private String locality;
      private String adminUnit;
      private String address;
      private Integer latitude;
      private Integer longitude;
      
      private Location(Builder builder) {
          this.county = builder.county;
          this.sirutaCode = builder.sirutaCode;
          this.locality = builder.locality;
          this.adminUnit = builder.adminUnit;
          this.address = builder.address;
          this.latitude = builder.latitude;
          this.longitude = builder.longitude;
      }
      
      public static class Builder {
          private String county;
          private Integer sirutaCode;
          private String locality;
          private String adminUnit;
          private String address;
          private Integer latitude;
          private Integer longitude;
      
          public Builder(String county, Integer sirutaCode) {
              this.county = county;
              this.sirutaCode = sirutaCode;
          }
          
          public Builder setLocality(String locality) {
              this.locality = locality;
              return this;
          }
          
          public Builder setAdminUnit(String adminUnit) {
              this.adminUnit = adminUnit;
              return this;
          }
          
          public Location build() {
              return new Location(this);
          }
      }
  }
  ```

  ```java
  public class Museum {
      private String name;
      private long code;
      private long supervisorCode;
      private Location location;
      
      private Museum(Builder builder) {
          this.name = builder.name;
          this.code = builder.code;
          this.supervisorCode = builder.supervisorCode;
          this.location = builder.location;
      }
      
      public static class Builder {
          private String name;
          private long code;
          private long supervisorCode;
          private Location location;
      
          public Builder(String name, long code, long supervisorCode, Location location) {
              this.name = name;
              this.code = code;
              this.supervisorCode = supervisorCode;
              this.location = location;
          }
          
          public Museum build() {
              return new Museum(this);
          }
      }
  }
  ```

### 3️⃣ **Observer** - `Group.java`
**Motivation:**
- Ensures that guides are notified about events organized by museums.
- Implementation:
  ```java
  public class Group implements Observer {
      private Professor guide;
      
      @Override
      public void update(String eventMessage) {
          if (guide != null) {
              System.out.println("To: " + guide.getEmail() + " ## Message: " + eventMessage);
          }
      }
  }
  ```

### 4️⃣ **Command** - `CommandProcessor.java`
**Motivation:**
- Allows the execution of commands without affecting the main structure of the program.
- Implementation:
  ```java
  public interface Command {
      String execute(String[] parts, Database database);
  }
  ```
  ```java
  public class AddMuseumCommand implements Command {
      @Override
      public String execute(String[] parts, Database database) {
          Museum museum = new Museum.Builder(parts[1], Long.parseLong(parts[2]), Long.parseLong(parts[3]), new Location.Builder(parts[4], Integer.parseInt(parts[5])).build()).build();
          database.addMuseum(museum);
          return "Museum added: " + parts[1];
      }
  }
  ```

## Implemented Functionalities
- **Add museums** (`ADD MUSEUM`)
- **Add tourist groups** (`ADD GROUP`)
- **Add guides and members** (`ADD GUIDE`, `ADD MEMBER`)
- **Search guides and members** (`FIND GUIDE`, `FIND MEMBER`)
- **Remove guides and members** (`REMOVE GUIDE`, `REMOVE MEMBER`)
- **Manage museum events** (`ADD EVENT`)

## 📌 Conclusion
This project follows OOP principles and implements **4 design patterns** for a clear and extensible architecture.
Using **Singleton, Builder, Observer, and Command** ensures scalability and ease of maintenance. 🚀
