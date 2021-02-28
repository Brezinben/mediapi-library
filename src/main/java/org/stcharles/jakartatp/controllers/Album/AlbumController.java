package org.stcharles.jakartatp.controllers.Album;

import org.stcharles.jakartatp.api.Album.AlbumOutput;

import java.time.LocalDate;
import java.util.List;

public interface AlbumController {

    /**
     * @param id the id
     * @return List<AlbumOutput>
     */
    List<AlbumOutput> getAll(int id);

    /**
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return AlbumOutput
     */
    AlbumOutput get(int groupId, int albumId);

    /**
     * @param title of album to find
     * @return List<AlbumOutput>
     */
    List<AlbumOutput> getByTitle(String title);

    AlbumOutput create(Integer groupId, String title, LocalDate release);
}
