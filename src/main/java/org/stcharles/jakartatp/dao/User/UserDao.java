package org.stcharles.jakartatp.dao.User;

import org.stcharles.jakartatp.model.User;

import java.util.List;

public interface UserDao {
    void persist(User user);

    List<User> getAll();

    User get(Integer id);

    Integer count();

    User getByEmail(String email);

    void remove(User user);
}
