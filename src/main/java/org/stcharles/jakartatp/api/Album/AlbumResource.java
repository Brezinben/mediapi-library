package org.stcharles.jakartatp.api.Album;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.Album.AlbumController;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Path("/groups/{groupId}/albums")
public class AlbumResource {
    @Inject
    @Prod
    private AlbumController albumController;

    @GET
    @Produces("application/json")
    public List<AlbumOutput> getAll(@PathParam("groupId") Integer groupId, @QueryParam("title") String title) {
        if (title != null) {
            return albumController.getByTitle(title);
        }
        return albumController.getAll(groupId);
    }

    @GET
    @Path("/{albumId}")
    @Produces("application/json")
    public AlbumOutput get(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId) {
        return albumController.get(groupId, albumId);
    }


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(@PathParam("groupId") Integer groupId, AlbumInput request) {
        AlbumOutput album = albumController.create(groupId, request.title, request.release);
        return Response
                .status(Response.Status.CREATED)
                .entity(album)
                .build();
    }

    @PUT
    @Path("/{albumId}")
    @Consumes("application/json")
    public Response update(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId, AlbumInput albumInput) {
        AlbumOutput album = albumController.update(groupId, albumId, albumInput.title, albumInput.release);
        return Response
                .status(Response.Status.OK)
                .entity(album)
                .build();
    }

    @DELETE
    @Path("/{albumId}")
    @Consumes("application/json")
    public Response remove(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId) {
        Boolean deleted = albumController.remove(groupId, albumId);
        Response.Status code = deleted ? Response.Status.NO_CONTENT : Response.Status.BAD_REQUEST;
        return Response.status(code).build();
    }


}
