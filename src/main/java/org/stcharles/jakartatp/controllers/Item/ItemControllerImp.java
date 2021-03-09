package org.stcharles.jakartatp.controllers.Item;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.controllers.Album.AlbumController;
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
    @Prod
    @Inject
    AlbumController albumController;

    @Inject
    @Prod
    private ItemDao itemDao;

    /**
     * Gets the wanted item
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @param itemId  the item identifier
     * @return the wanted item
     * @Override
     */
    public Item get(Integer groupId, Integer albumId, Integer itemId) {

        Album album = albumController.get(groupId, albumId);
        List<Item> items = Optional.ofNullable(album.getItems()).orElseThrow(NotFoundException::new);
        return items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(NotFoundException::new);

    }


    /**
     * Gets the one from album
     *
     * @param itemId the item identifier
     * @return the one from album
     * @Override
     */
    public Item get(Integer itemId) {
        return itemDao.get(itemId);
    }


    /**
     * Gets the all
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return all items
     * @Override
     */
    public List<Item> getAll(Integer groupId, Integer albumId) {
        return albumController.get(groupId, albumId).getItems();
    }


    /**
     * Create
     *
     * @param itemValue the item input
     * @param groupId   the group identifier
     * @param albumId   the album identifier
     * @return Item
     * @Override
     */

    public Item create(String[] itemValue, Integer groupId, Integer albumId) {

        ArrayList<String[]> item = new ArrayList<>();
        item.add(itemValue);
        return createMultiple(item, groupId, albumId).get(0);
    }


    /**
     * Create multiple
     *
     * @param items   the items
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return List<Item>
     * @Override
     */
    @Transactional
    public List<Item> createMultiple(List<String[]> items, Integer groupId, Integer albumId) {
        Album album = albumController.get(groupId, albumId);
        List<Item> toPersist = items.stream()
                .map(item -> new Item(ItemState.valueOf(item[0]), ItemType.valueOf(item[1]), album))
                .collect(Collectors.toList());
        toPersist.forEach(itemDao::persist);
        return toPersist;
    }


    /**
     * Update
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @param itemId  the item identifier
     * @param state   the state
     * @param type    the type
     * @return Item
     * @Override
     */
    @Transactional
    public Item update(Integer groupId, Integer albumId, Integer itemId, ItemState state, ItemType type) {

        Item item = get(groupId, albumId, itemId);
        item.setState(state);
        item.setType(type);
        return item;
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

        Item item = get(groupId, albumId, itemId);
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
}
