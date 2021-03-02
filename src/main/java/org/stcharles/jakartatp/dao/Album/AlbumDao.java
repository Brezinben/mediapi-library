package org.stcharles.jakartatp.dao.Album;

import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;

public interface AlbumDao {
    /**
     * @param album
     */
    void persist(Album album);

    /**
     * @param id
     * @return
     */
    Group get(Integer id);

}
