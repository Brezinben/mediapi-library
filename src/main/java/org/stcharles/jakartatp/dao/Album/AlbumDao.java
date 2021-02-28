package org.stcharles.jakartatp.dao.Album;

import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;

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
    Group get(Integer id);

    /**
     * @param group
     * @return
     */
    List<Album> getAlbumsFromGroup(Group group);

    /**
     * @param group
     * @param id
     * @return
     */
    Album getAlbumFromGroup(Group group, int id);
}
