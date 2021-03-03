package org.stcharles.jakartatp.api.Item;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.Item.ItemController;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Path("/groups/{groupId}/albums/{albumId}/items")
public class ItemResource {
    @Inject
    @Prod
    private ItemController itemController;

    @GET
    @Produces("application/json")
    public List<ItemOutput> getAll(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId) {
        return itemController.getAll(groupId, albumId);
    }

    @GET
    @Path("/{itemId}")
    @Produces("application/json")
    public ItemOutput getOneFromAlbum(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId, @PathParam("itemId") Integer itemId) {
        return itemController.getOneFromAlbum(groupId, albumId, itemId);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createMultiple(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId, List<ItemInput> request) {
        List<ItemOutput> items = itemController.createMultiple(request, groupId, albumId);
        return Response
                .status(Response.Status.CREATED)
                .entity(items)
                .build();
    }

    @PUT
    @Path("/{itemId}")
    @Consumes("application/json")
    public Response update(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId, @PathParam("itemId") Integer itemId, ChangeItemInput itemInput) {
        ItemOutput item = itemController.update(groupId, albumId, itemId, itemInput.state, itemInput.type);
        return Response
                .status(Response.Status.OK)
                .entity(item)
                .build();
    }

    @DELETE
    @Path("/{itemId}")
    @Consumes("application/json")
    public Response remove(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId, @PathParam("itemId") Integer itemId) {
        Boolean deleted = itemController.remove(groupId, albumId, itemId);
        Response.Status code = deleted ? Response.Status.NO_CONTENT : Response.Status.BAD_REQUEST;
        return Response.status(code).build();
    }

/*    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId,ItemInput request) {
        ItemOutput item = itemController.create(request,groupId,albumId);
        return Response
                .status(Response.Status.CREATED)
                .entity(item)
                .build();
    }*/
}