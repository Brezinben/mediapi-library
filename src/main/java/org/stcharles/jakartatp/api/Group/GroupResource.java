package org.stcharles.jakartatp.api.Group;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.Group.GroupController;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Path("/groups")
public class GroupResource {
    @Inject
    @Prod
    private GroupController groupController;

    @GET
    @Produces("application/json")
    public List<GroupOutput> getAll(@QueryParam("name") String name) {
        if (name != null) {
            return groupController.getByName(name);
        }
        return groupController.getAll();
    }


    @GET
    @Path("/{groupId}")
    @Produces("application/json")
    public GroupOutput get(@PathParam("groupId") Integer id) {
        return groupController.get(id);
    }


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(GroupInput request) {
        GroupOutput group = groupController.create(request.albums, request.name, request.createdAt);
        return Response
                .status(Response.Status.CREATED)
                .entity(group)
                .build();
    }

    @PUT
    @Path("/{groupId}")
    @Consumes("application/json")
    public Response update(@PathParam("groupId") Integer groupId, ChangeGroupInput groupInput) {
        GroupOutput group = groupController.update(groupId, groupInput.name, groupInput.createdAt);
        return Response
                .status(Response.Status.OK)
                .entity(group)
                .build();
    }
}
