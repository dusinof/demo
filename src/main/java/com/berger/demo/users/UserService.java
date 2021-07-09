package com.berger.demo.users;

import com.berger.demo.domain.Department;
import com.berger.demo.domain.Users;
import com.berger.demo.dto.DepartmentOverview;
import com.berger.demo.dto.ImmutableUserDto;
import com.berger.demo.dto.UserDto;
import com.berger.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public List<UserDto> findAllUsers() {
        var users = usersRepository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .map(user -> ImmutableUserDto.of(user.getName(), getDepartmentOverviews(user.getDepartments())))
                .collect(Collectors.toList());
    }

    private List<DepartmentOverview> getDepartmentOverviews(List<Department> departments) {
        return departments.stream()
                .map(dep -> DepartmentOverview.of(dep.getId(), dep.getInformation(), dep.getParentId().orElse(null)))
                .collect(Collectors.toList());

    }

    @Transactional
    public String deleteUser(String name) {
        return usersRepository.deleteByName(name) > 0 ? String.format("Successfully deleted user: %s", name) : String.format("Deleting of user: %s has failed.", name);
    }

    @Transactional
    public void createUser(String name) {
        usersRepository.save(new Users(name));
    }

    @Transactional
    public String deleteAllUsers() {
        usersRepository.deleteAll();
        return StreamSupport.stream(usersRepository.findAll().spliterator(), false)
                .count() > 0 ? "Delete all users failed." : "All users has been successfully deleted.";
    }

    public Users findUser(String name) {
        return usersRepository.findUserByName(name).orElseThrow(() -> new NotFoundException(String.format("User with name:%s was not found in database.", name)));

    }

}
