package org.stcharles.jakartatp.controllers.Item;

import org.stcharles.jakartatp.api.Item.ItemOutput;

import java.util.List;
import java.util.Optional;

public interface ItemController {
    ItemOutput create(String name, int sides);

    List<ItemOutput> getAll();

    Optional<ItemOutput> get(Integer id);

    List<ItemOutput> getAllFromAlbum(Integer albumId);

    ItemOutput getOneFromAlbum(Integer albumId, Integer itemId);
}
