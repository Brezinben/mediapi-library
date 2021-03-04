package org.stcharles.jakartatp.controllers.FuzzyFinding;

import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;

import java.util.List;

public interface FuzzyFinding {
    List<Album> getAlbumByTitle(Integer groupId, String query);

    List<Group> getGroupsByName(String query);
}
