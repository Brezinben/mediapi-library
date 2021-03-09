package org.stcharles.jakartatp.model;

import jakarta.persistence.*;
import org.stcharles.jakartatp.Event.Group.GroupListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The class Group
 */
@Entity
@Table(name = "groups")
@EntityListeners(GroupListener.class)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(targetEntity = Album.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @OrderBy(value = "release")
    private List<Album> albums = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDate created_at;


    /**
     * It is a constructor.
     *
     * @param name       the name
     * @param created_at the created_at
     */
    public Group(String name, LocalDate created_at) {
        this.name = name;
        this.created_at = created_at;
    }

    /**
     * Constructor used by CDI
     */
    protected Group() {
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
     * Gets the albums
     *
     * @return the albums
     */
    public List<Album> getAlbums() {
        return albums;
    }


    /**
     * Sets the albums
     *
     * @param albums the albums
     */
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }


    /**
     * Gets the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets the created_at
     *
     * @return the created_at
     */
    public LocalDate getCreated_at() {
        return created_at;
    }


    /**
     * Sets the created_at
     *
     * @param created_at the created_at
     */
    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}
