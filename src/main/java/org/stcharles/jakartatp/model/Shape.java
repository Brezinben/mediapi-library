package org.stcharles.jakartatp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shapes")
public class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "sides")
    private Integer sides;

    public Shape(String name, Integer sides) {
        this.name = name;
        this.sides = sides;
    }

    protected Shape() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSides() {
        return sides;
    }
}
