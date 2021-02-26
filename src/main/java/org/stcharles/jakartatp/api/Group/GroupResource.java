package org.stcharles.jakartatp.api.Group;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.api.Album.AlbumOutput;
import org.stcharles.jakartatp.controllers.Group.GroupController;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Path("/groups")
public class GroupResource {
    @Inject
    @Prod
    private GroupController groupController;

    @GET
    @Produces("application/json")
    public List<GroupOutput> getAll() {
        return groupController.getAll();
    }

    @GET
    @Path("/{groupId}")
    @Produces("application/json")
    public GroupOutput get(@PathParam("groupId") int id) {
        return groupController.get(id);
    }

    @GET
    @Path("/{groupId}/albums")
    @Produces("application/json")
    public List<AlbumOutput> getAlbums(@PathParam("groupId") int id) {
        return groupController.getAlbums(id);
    }

    @GET
    @Path("/{groupId}/albums/{albumId}")
    @Produces("application/json")
    public AlbumOutput getOneAlbums(@PathParam("groupId") int groupId, @PathParam("albumId") int albumId) {
        return groupController.getOneAlbum(groupId, albumId);
    }

    @POST
    @Consumes("application/json")
    public Response create(GroupInput request) {
        Group group = groupController.create(request.albums, request.name, request.created_at);
        return Response
                .status(Response.Status.CREATED)
                .entity(group)
                .build();
    }
}
