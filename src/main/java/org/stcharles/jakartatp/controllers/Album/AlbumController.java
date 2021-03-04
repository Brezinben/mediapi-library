package org.stcharles.jakartatp.controllers.Album;

import org.stcharles.jakartatp.api.Album.AlbumOutput;

import java.time.LocalDate;
import java.util.List;

public interface AlbumController {

    /**
     * @param id the id
     * @return List<AlbumOutput>
     */
    List<AlbumOutput> getAll(Integer id);

    /**
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return AlbumOutput
     */
    AlbumOutput get(Integer groupId, Integer albumId);

    /**
     * @param groupId
     * @param title   of album to find
     * @return List<AlbumOutput>
     */
    List<AlbumOutput> getByTitle(Integer groupId, String title);

    AlbumOutput create(Integer groupId, String title, LocalDate release);

    AlbumOutput update(Integer groupId, Integer albumId, String title, LocalDate release);

    Boolean remove(Integer groupId, Integer albumId);
}
