package org.stcharles.jakartatp.controllers.Item;

import org.stcharles.jakartatp.model.Item;
import org.stcharles.jakartatp.model.ItemState;
import org.stcharles.jakartatp.model.ItemType;

import java.util.List;

/**
 *
 */
public interface ItemController {
    Item get(Integer groupId, Integer albumId, Integer itemId);

    Item get(Integer itemId);

    List<Item> getAll(Integer groupId, Integer albumId);

    List<Item> createMultiple(List<Item> items, Integer groupId, Integer albumId);

    Item create(Item request, Integer groupId, Integer albumId);

    Item update(Integer groupId, Integer albumId, Integer itemId, ItemState state, ItemType type);

    Boolean remove(Integer groupId, Integer albumId, Integer itemId);
}
