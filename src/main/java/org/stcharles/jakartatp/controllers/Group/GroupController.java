package org.stcharles.jakartatp.controllers.Group;

import org.stcharles.jakartatp.api.Album.AlbumInput;
import org.stcharles.jakartatp.api.Group.GroupOutput;

import java.time.LocalDate;
import java.util.List;

public interface GroupController {
    /**
     * @return List<GroupOutput>
     */
    List<GroupOutput> getAll();

    /**
     * @param id the id
     * @return GroupOutput
     */
    GroupOutput get(int id);

    /**
     * @param albums    the albums
     * @param name      the name
     * @param createdAt the created_at
     * @return Group
     */
    GroupOutput create(List<AlbumInput> albums, String name, LocalDate createdAt);

    /**
     * @param name the name to find
     * @return List<GroupOutput>
     */
    List<GroupOutput> getByName(String name);

}
