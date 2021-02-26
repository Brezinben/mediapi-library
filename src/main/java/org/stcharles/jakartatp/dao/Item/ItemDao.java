package org.stcharles.jakartatp.dao.Item;

import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Item;

import java.util.List;

public interface ItemDao {
    void persist(Item shape);

    Item get(Integer id);

    List<Item> getAll();

    List<Item> getAllFromAlbum(Album album);

    Item getOneFromAlbum(Album album, Integer itemId);
}
