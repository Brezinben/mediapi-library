package org.stcharles.jakartatp.controllers.Item;

import org.stcharles.jakartatp.api.Item.ItemInput;
import org.stcharles.jakartatp.api.Item.ItemOutput;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface ItemController {
    ItemOutput create(String name, int sides);

    Optional<ItemOutput> get(Integer id);

    List<ItemOutput> getAll(Integer groupId, Integer albumId);

    ItemOutput getOneFromAlbum(Integer groupId, Integer albumId, Integer itemId);

    List<ItemOutput> createMultiple(List<ItemInput> request, Integer groupId, Integer albumId);

    ItemOutput create(ItemInput request, Integer groupId, Integer albumId);
}
