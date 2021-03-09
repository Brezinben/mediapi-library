package org.stcharles.jakartatp.api.Item;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.Item.ItemController;
import org.stcharles.jakartatp.model.Item;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/groups/{groupId}/albums/{albumId}/items")
public class ItemResource {
    @Inject
    @Prod
    private ItemController itemController;

    @GET
    @Produces("application/json")
    public List<ItemOutput> getAll(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId) {
        return itemController.getAll(groupId, albumId)
                .stream()
                .map(ItemOutput::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{itemId}")
    @Produces("application/json")
    public ItemOutput get(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId, @PathParam("itemId") Integer itemId) {
        Item item = itemController.get(groupId, albumId, itemId);
        return new ItemOutput(item);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createMultiple(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId, List<ItemInput> itemInputList) {

        //Si l'on veux crée plusieurs item d'un coup
        List<ItemInput> multiplyItemInput = new ArrayList<>();
        Integer toAdd;
        for (ItemInput itemInput : itemInputList) {
            toAdd = itemInput.nombre;
            if (toAdd < 0) continue;
            for (int j = 0; j < toAdd; j++) {
                multiplyItemInput.add(itemInput);
            }
        }

        List<String[]> itemsToCreate = multiplyItemInput.stream()
                .map(itemInput -> new String[]{itemInput.state.toString(), itemInput.type.toString()})
                .collect(Collectors.toList());

        List<ItemOutput> result = itemController.createMultiple(itemsToCreate, groupId, albumId)
                .stream().map(ItemOutput::new)
                .collect(Collectors.toList());

        return Response.status(Response.Status.CREATED)
                .entity(result)
                .build();
    }

    @PUT
    @Path("/{itemId}")
    @Consumes("application/json")
    public Response update(@PathParam("groupId") Integer groupId, @PathParam("albumId") Integer albumId, @PathParam("itemId") Integer itemId, ChangeItemInput itemInput) {
        Item item = itemController.update(groupId, albumId, itemId, itemInput.state, itemInput.type);
        return Response.status(Response.Status.OK)
                .entity(new ItemOutput(item))
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

}
