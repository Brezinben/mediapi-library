package org.stcharles.jakartatp.api.Album;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.Album.AlbumController;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The class Album resource
 */
@Path("/groups/{groupId}/albums")
public class AlbumResource {
    @Inject
    @Prod
    private AlbumController albumController;

    /**
     * Permet de ramener tout les albums liée à un groupe,
     * si le titre est renseigner alors on fera une recherche dessus.
     *
     * @param groupId l'identifiant du groupe
     * @param title   Le titre de l'album
     * @return AlbumOutput
     */
    @GET
    @Produces("application/json")
    public List<AlbumOutput> getAll(@PathParam("groupId") Integer groupId, @QueryParam("title") String title) {
        if (title != null) {
            return albumController.getByTitle(groupId, title)
                    .stream()
                    .map(AlbumOutput::new)
                    .collect(Collectors.toList());
        }
        return albumController.getAll(groupId)
                .stream()
                .map(AlbumOutput::new)
                .collect(Collectors.toList());
    }

    /**
     * @param groupId
     * @param albumId
     * @return
     */
    @GET
    @Path("/{albumId}")
    @Produces("application/json")
    public AlbumOutput get(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId) {
        Album album = albumController.get(groupId, albumId);
        return new AlbumOutput(album);
    }

    /**
     * @param groupId
     * @param request
     * @return
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(@PathParam("groupId") Integer groupId, AlbumInput request) {

        Album album = albumController.create(groupId, request.title, request.release);

        return Response.status(Response.Status.CREATED)
                .entity(new AlbumOutput(album))
                .build();
    }

    /**
     * @param groupId
     * @param albumId
     * @param albumInput
     * @return
     */
    @PUT
    @Path("/{albumId}")
    @Consumes("application/json")
    public Response update(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId, AlbumInput albumInput) {

        Album album = albumController.update(groupId, albumId, albumInput.title, albumInput.release);

        return Response.status(Response.Status.OK)
                .entity(new AlbumOutput(album))
                .build();
    }

    /**
     * @param groupId
     * @param albumId
     * @return
     */
    @DELETE
    @Path("/{albumId}")
    @Consumes("application/json")
    public Response remove(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId) {
        Boolean deleted = albumController.remove(groupId, albumId);
        Response.Status code = deleted ? Response.Status.NO_CONTENT : Response.Status.BAD_REQUEST;
        return Response.status(code).build();
    }

}
