package org.stcharles.jakartatp.controllers;

import org.stcharles.jakartatp.model.Shape;

import java.util.List;
import java.util.Optional;

public interface ShapeController {
    Shape create(String name, int sides);
    Shape rename(int id, String name);
    List<Shape> getAll();
    Optional<Shape> get(Integer id);
}
