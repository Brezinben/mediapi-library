package org.stcharles.jakartatp.controllers.Item;

import org.stcharles.jakartatp.api.Item.ItemInput;
import org.stcharles.jakartatp.api.Item.ItemOutput;
import org.stcharles.jakartatp.model.ItemState;
import org.stcharles.jakartatp.model.ItemType;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface ItemController {
    Optional<ItemOutput> get(Integer id);

    List<ItemOutput> getAll(Integer groupId, Integer albumId);

    ItemOutput getOneFromAlbum(Integer groupId, Integer albumId, Integer itemId);

    List<ItemOutput> createMultiple(List<ItemInput> request, Integer groupId, Integer albumId);

    ItemOutput create(ItemInput request, Integer groupId, Integer albumId);

    ItemOutput update(Integer groupId, Integer albumId, Integer itemId, ItemState state, ItemType type);
}
