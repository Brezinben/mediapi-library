package org.stcharles.jakartatp.controllers.User;

import org.stcharles.jakartatp.api.User.UserOutput;

import java.util.List;

public interface UserController {

    UserOutput create(String firstName, String lastName, String email);

    List<UserOutput> getAll();

    UserOutput get(Integer id);
}
