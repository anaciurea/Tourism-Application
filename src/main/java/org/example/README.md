# POO Project 2025 - Tourism Application

## Project Description
This application manages the database of museums and the organization of tourist groups.
It is responsible for adding, searching, and managing guides, visitors, and museums efficiently.

## Classes and exceptions:
Database, Museum, Person (Student and Professor extends Person), Location and Event are classes that were required.
Aditionally, GroupNotExistsException, GroupThresholdException, GuideExistsException, GuideTypeException, PersonNotExistsException are exceptions that extend Exception class. 

## Design Patterns Used
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

### 3️⃣ **Factory Method** - `PersonFactory.java`
**Motivation:**
- Centralizes the creation of `Student` and `Professor` objects, simplifying object instantiation and reducing code duplication.
- Implementation:
  ```java
  public class PersonFactory {
      public static Person createPerson(String surname, String name, String role, int age, String email, String school, int studyYear, int experience) {
          if (role.equalsIgnoreCase("student")) {
              return new Student(surname, name, role, school, studyYear);
          } else if (role.equalsIgnoreCase("professor")) {
              return new Professor(surname, name, role, school, experience);
          }
          throw new IllegalArgumentException("Invalid role type");
      }
  }
  ```

### 4️⃣ **Observer** - `Group.java`
**Motivation:**
-  Define a one-to-many dependency between
   objects so that all dependents are notified & updated
   when one object changes state.
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

### 5️⃣ **Command** - `CommandProcessor.java`
**Motivation:**
-  Encapsulate the request for a service.
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

## Conclusion
This project follows OOP principles and implements **5 design patterns** for a clear and extensible architecture.
Using **Singleton, Builder, Factory Method, Observer, and Command** ensures scalability and ease of maintenance.