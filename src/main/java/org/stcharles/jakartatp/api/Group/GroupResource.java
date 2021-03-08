package org.stcharles.jakartatp.api.Group;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.Group.GroupController;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;
import java.util.stream.Collectors;


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
            return groupController.getByName(name)
                    .stream()
                    .map(GroupOutput::new)
                    .collect(Collectors.toList());
        }
        return groupController.getAll()
                .stream()
                .map(GroupOutput::new)
                .collect(Collectors.toList());
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
        Group group = groupController.get(id);
        return new GroupOutput(group);
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
        Group group = groupController.create(request.name, request.createdAt);
        return Response.status(Response.Status.CREATED)
                .entity(new GroupOutput(group))
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

        Group group = groupController.update(groupId, groupInput.name, groupInput.createdAt);

        return Response.status(Response.Status.OK)
                .entity(new GroupOutput(group))
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
