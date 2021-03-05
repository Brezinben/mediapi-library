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

    /**
     * Get all group
     * if name exist, fuzzy finding will be execute
     *
     * @param name
     * @return
     */
    @GET
    @Produces("application/json")
    public List<GroupOutput> getAll(@QueryParam("name") String name) {
        if (name != null) {
            return groupController.getByName(name);
        }
        return groupController.getAll();
    }

    /**
     * Get the group with the given id
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{groupId}")
    @Produces("application/json")
    public GroupOutput get(@PathParam("groupId") Integer id) {
        return groupController.get(id);
    }

    /**
     * Create a group
     *
     * @param request
     * @return
     */
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

    /**
     * Update a group
     *
     * @param groupId
     * @param groupInput
     * @return
     */
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

    /**
     * Delete a group
     *
     * @param groupId
     * @return
     */
    @DELETE
    @Path("/{groupId}")
    @Consumes("application/json")
    public Response remove(@PathParam("groupId") Integer groupId) {
        Boolean deleted = groupController.remove(groupId);
        Response.Status code = deleted ? Response.Status.NO_CONTENT : Response.Status.BAD_REQUEST;
        return Response.status(code).build();
    }
}
