# demo project

### Missing: Tests. TBD

###Used:
 - Java 11, Spring Boot, Hibernate, Spock, Groovy, Gradle, Json, Immutables

Simply Run the application. Then use following REST calls:


### Import Data(POST)
#### Can be called multiple times in the row. It always clean up existing records.
- http://localhost:8080/initialize

### Get Users (GET)
- http://localhost:8080/users 

### Get Departments(GET)
- http://localhost:8080/departments

### Delete User (DELETE). It might delete an respective department if user is owner of it.
- http://localhost:8080/user/David 

### Delete All Users (DELETE). Delete All Users & related Departments.
- http://localhost:8080/users 

### Delete Department (DELETE).
#### It's possible to delete only those departments, who user Bill Is owner or parent.
- http://localhost:8080/department/2

User Bill is hardcoded as an Logged in users.

###DB SCHEMA:
<code>

    CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    UNIQUE(name));

</code>
<code>

    CREATE TABLE department (
    id BIGINT AUTO_INCREMENT  PRIMARY KEY,
    information VARCHAR(250) NOT NULL,
    owner_id BIGINT NOT NULL,
    parent_id BIGINT DEFAULT NULL,
    foreign key (owner_id) references users(id));

</code>
