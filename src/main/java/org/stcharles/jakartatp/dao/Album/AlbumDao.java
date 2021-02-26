package org.stcharles.jakartatp.dao.Album;

import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;

import java.util.List;

public interface AlbumDao {
    void persist(Album album);

    Group get(Integer id);

    List<Album> getAlbumsFromGroup(Group group);

    Album getAlbumFromGroup(Group group, int id);
}
