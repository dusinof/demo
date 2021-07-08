package com.berger.demo;

import com.berger.demo.department.DepartmentService;
import com.berger.demo.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InitializeDataService {

    private static final Logger log = LoggerFactory.getLogger(InitializeDataService.class);
    private final DepartmentService departmentService;
    private final UserService userService;

    public InitializeDataService(DepartmentService departmentService, UserService userService) {
        this.departmentService = departmentService;
        this.userService = userService;
    }


    @Transactional
    public void importData() {
        cleanup();
        createUsers();
        createDepartments();
    }

    // Clean table first if data was imported previously
    private void cleanup() {
        log.info("Delete all existing records.");
        departmentService.deleteAllDepartments();
        userService.deleteAllUsers();
    }

    private void createUsers() {
        log.info("Creating users.");
        List.of("Bill", "Tom", "Mark", "Jane", "David").forEach(userService::createUser);
    }


    private void createDepartments() {
        log.info("Creating departments.");
        departmentService.createDepartment("Marketing", userService.findUser("Bill"), null);
        departmentService.createDepartment("Business", userService.findUser("Tom"), 1L);
        departmentService.createDepartment("Design", userService.findUser("Mark"), 2L);
        departmentService.createDepartment("Finance", userService.findUser("Jane"), 3L);
        departmentService.createDepartment("HR", userService.findUser("Mark"), null);
        departmentService.createDepartment("Development", userService.findUser("Jane"), null);
    }


}
