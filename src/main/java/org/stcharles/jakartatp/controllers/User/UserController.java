package org.stcharles.jakartatp.controllers.User;

import jakarta.xml.bind.ValidationException;
import org.stcharles.jakartatp.api.User.UserOutput;

import java.util.ArrayList;
import java.util.List;

public interface UserController {

    UserOutput create(String firstName, String lastName, String email) throws ValidationException;

    List<UserOutput> getAll();

    UserOutput get(Integer id);

    Boolean delete(int userId);

    ArrayList<UserOutput> getByEmail(String email);
}
