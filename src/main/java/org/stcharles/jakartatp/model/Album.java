package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "albums")
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

    @OneToMany
    private List<Item> items;

    public Album(Group group, String title, LocalDate release, List<Item> items) {
        this.group = group;
        this.title = title;
        this.release = release;
        this.items = items;
    }

    protected Album() {
    }

    public Integer getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getRelease() {
        return release;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
