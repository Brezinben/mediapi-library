package org.stcharles.jakartatp.controllers.FuzzyFinding;

import jakarta.enterprise.event.Observes;
import org.stcharles.jakartatp.Event.Album.AlbumChanged;
import org.stcharles.jakartatp.Event.Group.GroupChanged;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;

import java.util.List;

public interface FuzzyFinding {
    List<Album> getAlbumByTitle(Integer groupId, String query);

    List<Group> getGroupsByName(String query);

    void updateGroups(@Observes GroupChanged g);

    void updateAlbums(@Observes AlbumChanged a);

}
