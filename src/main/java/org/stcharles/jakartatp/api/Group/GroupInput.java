package org.stcharles.jakartatp.api.Group;

import org.stcharles.jakartatp.api.Album.AlbumInput;

import java.time.LocalDate;
import java.util.List;

public class GroupInput {
    public List<AlbumInput> albums;
    public String name;
    public LocalDate createdAt;
}
