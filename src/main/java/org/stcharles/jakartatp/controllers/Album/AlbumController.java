package org.stcharles.jakartatp.controllers.Album;

import org.stcharles.jakartatp.model.Album;

import java.time.LocalDate;
import java.util.List;

public interface AlbumController {

    /**
     * @param id the id
     * @return List<Album>
     */
    List<Album> getAll(Integer id);

    /**
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return Album
     */
    Album get(Integer groupId, Integer albumId);


    /**
     * @param groupId
     * @param title   of album to find
     * @return List<Album>
     */
    List<Album> getByTitle(Integer groupId, String title);

    Album create(Integer groupId, String title, LocalDate release);

    Album update(Integer groupId, Integer albumId, String title, LocalDate release);

    Boolean remove(Integer groupId, Integer albumId);
}
