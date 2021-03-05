package org.stcharles.jakartatp.dao.Album;

import org.stcharles.jakartatp.model.Album;

import java.util.List;

public interface AlbumDao {
    /**
     * @param album
     */
    void persist(Album album);

    /**
     * @param id
     * @return
     */
    Album get(Integer id);

    void remove(Album album);

    List<Album> getAll();

    void refresh();
}
