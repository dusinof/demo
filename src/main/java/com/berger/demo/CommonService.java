package com.berger.demo;

import com.berger.demo.domain.Users;
import com.berger.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService {

    private final UsersRepository usersRepository;

    public CommonService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Dummy class returning logged in users.
     *
     * @return always Bill
     */
    public Users getLoggedUser() {
        return usersRepository.findUserByName("Bill").orElseThrow(() -> new NotFoundException("Super user Bill was not found in DB."));
    }

}
