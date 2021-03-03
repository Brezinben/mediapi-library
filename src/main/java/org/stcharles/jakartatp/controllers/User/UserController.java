package org.stcharles.jakartatp.controllers.User;

import jakarta.xml.bind.ValidationException;
import org.stcharles.jakartatp.api.User.UserOutput;

import java.util.List;

public interface UserController {

    UserOutput create(String firstName, String lastName, String email) throws ValidationException;

    List<UserOutput> getAll();

    UserOutput get(Integer id);

    Boolean delete(Integer userId);

    List<UserOutput> getByEmail(String email);

    UserOutput update(Integer id, String firstName, String lastName, String email);

    Boolean remove(Integer userId);
}
