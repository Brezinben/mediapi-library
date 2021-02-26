package org.stcharles.jakartatp.api.Album;

import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.model.Item;

import java.time.LocalDate;
import java.util.List;

public class AlbumInput {
    public Group group;
    public String title;
    public LocalDate release;
    public List<Item> items;
}
