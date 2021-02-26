package org.stcharles.jakartatp.dao.Shape;

import org.stcharles.jakartatp.model.Shape;

import java.util.List;

public interface ShapeDao {
    void persist(Shape shape);

    List<Shape> getAll();

    Shape get(Integer id);
}
