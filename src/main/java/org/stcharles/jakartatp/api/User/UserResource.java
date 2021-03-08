package org.stcharles.jakartatp.api.User;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.ValidationException;
import org.stcharles.jakartatp.controllers.User.UserController;
import org.stcharles.jakartatp.model.User;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
public class UserResource {
    @Inject
    @Prod
    private UserController userController;

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public List<UserOutput> getAll(@QueryParam("email") String email) {
        if (email != null) {
            User user = userController.getByEmail(email);
            ArrayList<UserOutput> result = new ArrayList<>();
            result.add(new UserOutput(user));
            return result;
        }
        return userController.getAll()
                .stream()
                .map(UserOutput::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{userId}")
    @Produces("application/json")
    public UserOutput get(@PathParam("userId") Integer userId) {
        User user = userController.get(userId);
        return new UserOutput(user);
    }


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(UserInput request) throws ValidationException {
        User user = userController.create(request.firstName, request.lastName, request.email);
        return Response
                .status(Response.Status.CREATED)
                .entity(new UserOutput(user))
                .build();
    }

    @PUT
    @Path("/{userId}")
    @Consumes("application/json")
    public Response update(@PathParam("userId") Integer userId, UserInput userInput) {
        User user = userController.update(userId, userInput.firstName, userInput.lastName, userInput.email);
        return Response
                .status(Response.Status.OK)
                .entity(new UserOutput(user))
                .build();
    }

    @DELETE
    @Path("/{userId}")
    @Consumes("application/json")
    public Response remove(@PathParam("userId") Integer userId) {
        Boolean deleted = userController.remove(userId);
        Response.Status code = deleted ? Response.Status.NO_CONTENT : Response.Status.BAD_REQUEST;
        return Response.status(code).build();
    }
}