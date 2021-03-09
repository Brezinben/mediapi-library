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

    List<Item> createMultiple(List<String[]> itemsValues, Integer groupId, Integer albumId);

    Item create(String[] itemValue, Integer groupId, Integer albumId);

    Item update(Integer groupId, Integer albumId, Integer itemId, ItemState state, ItemType type);

    Boolean remove(Integer groupId, Integer albumId, Integer itemId);
}
