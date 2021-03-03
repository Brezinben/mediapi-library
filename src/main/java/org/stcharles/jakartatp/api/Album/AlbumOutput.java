package org.stcharles.jakartatp.api.Album;

import org.stcharles.jakartatp.api.Group.GroupOutput;
import org.stcharles.jakartatp.model.Album;

import java.time.LocalDate;

public class AlbumOutput {
    public String title;
    public LocalDate release;
    public GroupOutput group;
    public Integer id;

    public AlbumOutput(Album a) {
        this.id = a.getId();
        this.title = a.getTitle();
        this.release = a.getRelease();
    }
}
