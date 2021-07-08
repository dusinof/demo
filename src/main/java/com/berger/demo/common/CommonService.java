package com.berger.demo.common;

import com.berger.demo.domain.Users;
import com.berger.demo.exception.NotFoundException;
import com.berger.demo.users.UsersRepository;
import org.springframework.stereotype.Service;

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
