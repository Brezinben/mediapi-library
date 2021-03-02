package org.stcharles.jakartatp.api.User;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.ValidationException;
import org.stcharles.jakartatp.controllers.User.UserController;

import java.util.List;

@Path("/users")
public class UserResource {
    @Inject
    private UserController userController;

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public List<UserOutput> getAll(@QueryParam("email") String email) {
        if (email != null) {
            return userController.getByEmail(email);
        }
        return userController.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public UserOutput get(@PathParam("id") Integer id) {
        return userController.get(id);
    }


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(UserInput request) throws ValidationException {
        UserOutput user = userController.create(request.firstName, request.lastName, request.email);
        return Response
                .status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") Integer id, UserInput userInput) {
        UserOutput user = userController.update(id, userInput.firstName, userInput.lastName, userInput.email);
        return Response
                .status(Response.Status.OK)
                .entity(user)
                .build();
    }
}