package org.stcharles.jakartatp.api.Album;

import com.sun.xml.internal.ws.developer.Serialization;
import org.stcharles.jakartatp.model.Album;

import java.time.LocalDate;

@Serialization(encoding = "json")
public class AlbumOutput {
    public String title;
    public LocalDate release;
    public Integer id;
    public Integer group;


    /**
     * It is a constructor.
     *
     * @param a the a
     */
    public AlbumOutput(Album a) {
        this.id = a.getId();
        this.title = a.getTitle();
        this.release = a.getRelease();
        this.group = a.getGroup().getId();
    }
}
