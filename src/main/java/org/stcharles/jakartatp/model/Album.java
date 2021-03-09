package org.stcharles.jakartatp.model;

import jakarta.persistence.*;
import org.stcharles.jakartatp.Event.Album.AlbumListener;

import java.time.LocalDate;
import java.util.List;

/**
 * The class Album
 */
@Entity
@Table(name = "albums")
@EntityListeners(AlbumListener.class)
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "title")
    private String title;

    @Column(name = "release")
    private LocalDate release;

    @OneToMany(targetEntity = Item.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id", nullable = false)
    @OrderBy(value = "cratedAt")
    private List<Item> items;


    /**
     * It is a constructor.
     *
     * @param group   the group
     * @param title   the title
     * @param release the release
     * @param items   the items
     */
    public Album(Group group, String title, LocalDate release, List<Item> items) {
        this.group = group;
        this.title = title;
        this.release = release;
        this.items = items;
    }

    /**
     * Constructor used by CDI
     */
    protected Album() {
    }

    /**
     * Gets the identifier
     *
     * @return the identifier
     */
    public Integer getId() {
        return id;
    }


    /**
     * Gets the group
     *
     * @return the group
     */
    public Group getGroup() {
        return group;
    }


    /**
     * Gets the title
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }


    /**
     * Sets the title
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Gets the release
     *
     * @return the release
     */
    public LocalDate getRelease() {
        return release;
    }


    /**
     * Sets the release
     *
     * @param release the release
     */
    public void setRelease(LocalDate release) {
        this.release = release;
    }


    /**
     * Gets the items
     *
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }


}
