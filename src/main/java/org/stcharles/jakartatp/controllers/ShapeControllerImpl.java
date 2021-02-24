package org.stcharles.jakartatp.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.stcharles.jakartatp.dao.ShapeDao;
import org.stcharles.jakartatp.model.Shape;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ShapeControllerImpl implements ShapeController {
    @Inject
    private ShapeDao shapeDao;

    @Override
    @Transactional
    public Shape create(String name, int sides) {
        Shape shape = new Shape(name, sides);
        shapeDao.persist(shape);
        return shape;
    }

    @Override
    @Transactional
    public Shape rename(int id, String name) {
        Shape shape = shapeDao.get(id);
        shape.setName(name);
        return shape;
    }

    @Override
    public List<Shape> getAll() {
        return shapeDao.getAll();
    }

    @Override
    public Optional<Shape> get(Integer id) {
        Shape maybeShape = shapeDao.get(id);
        return Optional.ofNullable(maybeShape);
    }
}
