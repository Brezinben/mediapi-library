package org.stcharles.jakartatp.api.Shape;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.Shape.ShapeController;
import org.stcharles.jakartatp.model.Shape;

import java.util.List;

@Path("/shapes")
public class ShapeResource {
    @Inject
    private ShapeController shapeController;

    @GET
    @Produces("application/json")
    public List<Shape> getAll() {
        return shapeController.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Shape get(@PathParam("id") int id) {
        return shapeController
                .get(id)
                .orElseThrow(NotFoundException::new);
    }

    @POST
    @Consumes("application/json")
    public Response create(ShapeInput input) {
        Shape shape = shapeController.create(input.name, input.sides);
        return Response
                .status(Response.Status.CREATED)
                .entity(shape)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") int id, ShapeInput input) {
        Shape shape = shapeController.rename(id, input.name);
        return Response
                .status(Response.Status.OK)
                .entity(shape)
                .build();
    }
}