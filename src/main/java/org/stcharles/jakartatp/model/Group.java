package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(targetEntity = Album.class)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private List<Album> albums = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDate created_at;

    public Group(String name, LocalDate created_at) {
        this.name = name;
        this.created_at = created_at;
    }

    protected Group() {
    }

    public Integer getId() {
        return id;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }
}
