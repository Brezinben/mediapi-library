package org.stcharles.jakartatp.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.stcharles.jakartatp.model.Shape;

import java.util.List;

@ApplicationScoped
public class ShapeDaoImpl implements ShapeDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Shape shape) {
        em.persist(shape);
    }

    @Override
    public List<Shape> getAll() {
        return em
                .createQuery("select s from Shape s", Shape.class)
                .getResultList();
    }

    @Override
    public Shape get(Integer id) {
        return em.find(Shape.class, id);
    }
}
