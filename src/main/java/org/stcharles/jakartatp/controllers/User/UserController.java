package org.stcharles.jakartatp.controllers.User;

import jakarta.xml.bind.ValidationException;
import org.stcharles.jakartatp.model.User;

import java.util.List;

public interface UserController {

    User create(String firstName, String lastName, String email) throws ValidationException;

    List<User> getAll();

    User get(Integer id);

    User getByEmail(String email);

    User update(Integer id, String firstName, String lastName, String email);

    Boolean remove(Integer userId);
}
