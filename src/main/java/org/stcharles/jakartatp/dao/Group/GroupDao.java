package org.stcharles.jakartatp.dao.Group;

import org.stcharles.jakartatp.model.Group;

import java.util.List;

public interface GroupDao {
    void persist(Group group);

    List<Group> getAll();

    Group get(Integer id);

    void remove(Group group);

    List<String> getAllName();
}
