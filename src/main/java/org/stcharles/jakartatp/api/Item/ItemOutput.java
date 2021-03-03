package org.stcharles.jakartatp.api.Item;

import org.stcharles.jakartatp.model.Item;
import org.stcharles.jakartatp.model.ItemState;
import org.stcharles.jakartatp.model.ItemType;

import java.time.LocalDate;

public class ItemOutput {
    public ItemState state;
    public ItemType type;
    public LocalDate cratedAt;
    public Integer id;

    public ItemOutput(Item i) {
        this.state = i.getState();
        this.type = i.getType();
        this.cratedAt = i.getCratedAt();
        this.id = i.getId();
    }

}
