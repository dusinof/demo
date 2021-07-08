# demo project

Used:
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

