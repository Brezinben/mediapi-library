package org.stcharles.jakartatp.controllers.Group;

import org.stcharles.jakartatp.api.Album.AlbumOutput;
import org.stcharles.jakartatp.api.Group.GroupOutput;
import org.stcharles.jakartatp.api.Item.ItemOutput;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;

import java.time.LocalDate;
import java.util.List;

public interface GroupController {
    List<GroupOutput> getAll();

    GroupOutput get(int id);

    List<AlbumOutput> getAlbums(int id);

    AlbumOutput getOneAlbum(int groupId, int albumId);

    Group create(List<Album> albums, String name, LocalDate created_at);

    List<ItemOutput> getItems(int groupId, int albumId);

    ItemOutput getOneItem(int groupId, int albumId, int itemId);
}
