package org.stcharles.jakartatp.controllers.Item;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.api.Item.ItemInput;
import org.stcharles.jakartatp.api.Item.ItemOutput;
import org.stcharles.jakartatp.dao.Album.AlbumDao;
import org.stcharles.jakartatp.dao.Group.GroupDao;
import org.stcharles.jakartatp.dao.Item.ItemDao;
import org.stcharles.jakartatp.model.*;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * The class Item controller imp implements item controller
 */
@Prod
@ApplicationScoped
public class ItemControllerImp implements ItemController {
    @Inject
    @Prod
    private GroupDao groupDao;

    @Inject
    @Prod
    private AlbumDao albumDao;

    @Inject
    @Prod
    private ItemDao itemDao;


    /**
     * Gets the
     *
     * @param id the id
     * @return the
     */
    @Override
    public Optional<ItemOutput> get(Integer id) {

        return Optional.empty();
    }


    /**
     * Gets the all
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return the all
     * @Override
     */
    public List<ItemOutput> getAll(Integer groupId, Integer albumId) {

        return getWantedAlbum(groupId, albumId)
                .getItems()
                .stream()
                .map(ItemOutput::new)
                .collect(Collectors.toList());
    }


    /**
     * Gets the one from album
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @param itemId  the item identifier
     * @return the one from album
     * @Override
     */
    public ItemOutput getOneFromAlbum(Integer groupId, Integer albumId, Integer itemId) {

        Item item = getWantedItem(groupId, albumId, itemId);
        return new ItemOutput(item);
    }


    /**
     * Create
     *
     * @param itemInput the item input
     * @param groupId   the group identifier
     * @param albumId   the album identifier
     * @return ItemOutput
     * @Override
     */

    public ItemOutput create(ItemInput itemInput, Integer groupId, Integer albumId) {

        ArrayList<ItemInput> itemIn = new ArrayList<ItemInput>();
        itemIn.add(itemInput);
        return createMultiple(itemIn, groupId, albumId).get(0);
    }


    /**
     * Create multiple
     *
     * @param items   the items
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return List<ItemOutput>
     * @Override
     */
    @Transactional
    public List<ItemOutput> createMultiple(List<ItemInput> items, Integer groupId, Integer albumId) {

        Album album = getWantedAlbum(groupId, albumId);

        //Si l'on veux crée plusieurs item d'un coup
        List<ItemInput> itemInputList = new ArrayList<>();

        for (ItemInput itemInput : items) {
            Integer toAdd = itemInput.nombre;
            if (toAdd < 0) continue;
            for (int j = 0; j < toAdd; j++) {
                itemInputList.add(itemInput);
            }
        }

        List<Item> persistItems = itemInputList.stream()
                .map(itemInput -> new Item(itemInput.state, itemInput.type, album))
                .collect(Collectors.toList());

        persistItems.forEach(itemDao::persist);

        return persistItems.stream()
                .map(ItemOutput::new)
                .collect(Collectors.toList());
    }


    /**
     * Update
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @param itemId  the item identifier
     * @param state   the state
     * @param type    the type
     * @return ItemOutput
     * @Override
     */
    @Transactional
    public ItemOutput update(Integer groupId, Integer albumId, Integer itemId, ItemState state, ItemType type) {

        Item item = getWantedItem(groupId, albumId, itemId);
        item.setState(state);
        item.setType(type);
        return new ItemOutput(item);
    }


    /**
     * Remove
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @param itemId  the item identifier
     * @return Boolean
     * @Override
     */
    @Transactional
    public Boolean remove(Integer groupId, Integer albumId, Integer itemId) {

        Item item = getWantedItem(groupId, albumId, itemId);
        Optional<Loan> loan = Optional.ofNullable(item.getLoan());
        if (loan.isPresent()) {
            throw new ValidationException("Cette item est actuellement emprunté on ne peux donc le supprimer");
        }

        try {
            itemDao.remove(item);
            return true;
        } catch (Exception exception) {
            Logger.getAnonymousLogger(exception.getMessage());
            return false;
        }
    }


    /**
     * Try to get the wanted album
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return Album
     * @Override
     */
    private Album getWantedAlbum(Integer groupId, Integer albumId) {

        Group group = Optional.ofNullable(groupDao.get(groupId)).orElseThrow(NotFoundException::new);
        List<Album> albums = Optional.ofNullable(group.getAlbums()).orElseThrow(NotFoundException::new);
        return albums.stream()
                .filter(album -> album.getId().equals(albumId))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }


    /**
     * Gets the wanted item
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @param itemId  the item identifier
     * @return the wanted item
     */
    private Item getWantedItem(Integer groupId, Integer albumId, Integer itemId) {

        Album album = getWantedAlbum(groupId, albumId);
        List<Item> items = Optional.ofNullable(album.getItems()).orElseThrow(NotFoundException::new);
        return items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(NotFoundException::new);

    }
}
