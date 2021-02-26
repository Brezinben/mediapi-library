package org.stcharles.jakartatp.controllers.User;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.api.User.UserOutput;
import org.stcharles.jakartatp.dao.User.UserDao;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserControllerImp implements UserController {
    @Inject
    @Prod
    private UserDao userDao;


    @Override
    public UserOutput create(String firstName, String lastName, String email) {
        return null;
    }

    @Override
    public List<UserOutput> getAll() {
        return userDao.getAll()
                .stream()
                .map(UserOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserOutput get(Integer id) {
        return Optional.ofNullable(userDao.get(id))
                .map(UserOutput::new)
                .orElseThrow(NotFoundException::new);
    }
}
