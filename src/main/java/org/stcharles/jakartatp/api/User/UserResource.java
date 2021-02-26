package org.stcharles.jakartatp.api.User;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.User.UserController;

import java.util.List;

@Path("/users")
public class UserResource {
    @Inject
    private UserController userController;


    @GET
    @Produces("application/json")
    public List<UserOutput> getAll() {
        return userController.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public UserOutput get(@PathParam("id") int id) {
        return userController.get(id);
    }

    @POST
    @Consumes("application/json")
    public Response create(UserInput request) {
        UserOutput user = userController.create(request.firstName, request.lastName, request.email);
        return Response
                .status(Response.Status.CREATED)
                .entity(user)
                .build();
    }
}