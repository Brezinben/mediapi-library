package org.stcharles.jakartatp.api.Group;

import org.stcharles.jakartatp.model.Album;

import java.time.LocalDate;
import java.util.List;

public class GroupInput {
    public List<Album> albums;
    public String name;
    public LocalDate created_at;
}
