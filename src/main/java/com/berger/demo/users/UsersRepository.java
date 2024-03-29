package com.berger.demo.users;

import com.berger.demo.domain.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

    Optional<Users> findUserByName(String name);

    //return number of deleted records
    long deleteByName(String name);

}
