package org.stcharles.jakartatp.controllers.Group;

import org.stcharles.jakartatp.model.Group;

import java.time.LocalDate;
import java.util.List;

public interface GroupController {
    /**
     * @return List<GroupOutput>
     */
    List<Group> getAll();

    /**
     * @param id the id
     * @return GroupOutput
     */
    Group get(Integer id);

    /**
     * @param name      the name
     * @param createdAt the created_at
     * @return Group
     */
    Group create(String name, LocalDate createdAt);

    /**
     * @param name the name to find
     * @return List<GroupOutput>
     */
    List<Group> getByName(String name);

    Group update(Integer groupId, String name, LocalDate createdAt);

    Boolean remove(Integer groupId);
}
