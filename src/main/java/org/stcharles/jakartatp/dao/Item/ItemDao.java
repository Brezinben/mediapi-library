package org.stcharles.jakartatp.dao.Item;

import org.stcharles.jakartatp.model.Item;

import java.util.List;

public interface ItemDao {
    void persist(Item shape);

    Item get(Integer id);

    List<Item> getAll();

}
