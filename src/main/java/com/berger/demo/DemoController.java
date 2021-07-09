package com.berger.demo;

import com.berger.demo.department.DepartmentService;
import com.berger.demo.dto.DepartmentDto;
import com.berger.demo.dto.UserDto;
import com.berger.demo.response.GenericResponse;
import com.berger.demo.response.ImmutableGenericResponse;
import com.berger.demo.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);
    private final InitializeDataService initializeDataService;
    private final UserService userService;
    private final DepartmentService departmentService;


    public DemoController(InitializeDataService initializeDataService, UserService userService, DepartmentService departmentService) {
        this.initializeDataService = initializeDataService;
        this.userService = userService;
        this.departmentService = departmentService;
    }


    @PostMapping("/initialize")
    public GenericResponse<String> initialize() {
        log.info("Received request to import dummy Data.");
        initializeDataService.importData();
        return ImmutableGenericResponse.of("Data successfully created.");
    }

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        log.info("Received request to get all users in database.");
        return userService.findAllUsers();
    }

    @DeleteMapping("/user/{userName}")
    public GenericResponse<String> deleteUserByName(@PathVariable String userName) {
        log.info("Received request to delete user: {}", userName);
        return ImmutableGenericResponse.of(userService.deleteUser(userName));
    }

    @DeleteMapping("/users")
    public GenericResponse<String> deleteAllUsers() {
        log.info("Received request to delete all users");
        return ImmutableGenericResponse.of(userService.deleteAllUsers());
    }

    @GetMapping("/departments")
    public List<DepartmentDto> getDepartments() {
        log.info("Received request to get all departments");
        return departmentService.findAllDepartments();
    }

    @DeleteMapping("/department/{departmentId}")
    public GenericResponse<String> deleteDepartment(@PathVariable Long departmentId) {
        log.info("Received request to delete department Id:{}", departmentId);
        return departmentService.deleteDepartment(departmentId);
    }

}
