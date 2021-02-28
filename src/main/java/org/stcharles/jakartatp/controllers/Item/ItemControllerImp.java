package org.stcharles.jakartatp.controllers.Item;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.api.Item.ItemInput;
import org.stcharles.jakartatp.api.Item.ItemOutput;
import org.stcharles.jakartatp.dao.Album.AlbumDao;
import org.stcharles.jakartatp.dao.Group.GroupDao;
import org.stcharles.jakartatp.dao.Item.ItemDao;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.model.Item;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Prod
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

    @Override
    public ItemOutput create(String name, int sides) {
        return null;
    }

    @Override
    public Optional<ItemOutput> get(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<ItemOutput> getAll(Integer groupId, Integer albumId) {
        Album album = this.getWantedAlbum(groupId, albumId);
        return itemDao.getAllFromAlbum(album)
                .stream()
                .map(ItemOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public ItemOutput getOneFromAlbum(Integer groupId, Integer albumId, Integer itemId) {
        Album album = this.getWantedAlbum(groupId, albumId);
        Optional<Item> item = Optional.ofNullable(itemDao.getOneFromAlbum(album, itemId));
        return new ItemOutput(item.orElseThrow(NotFoundException::new));
    }

    @Override
    //Je ne le met pas Transactional vu que createMultiple l'es.
    public ItemOutput create(ItemInput itemInput, Integer groupId, Integer albumId) {
        ArrayList<ItemInput> itemIn = new ArrayList<ItemInput>();
        itemIn.add(itemInput);
        return createMultiple(itemIn, groupId, albumId).get(0);
    }

    @Override
    @Transactional
    public List<ItemOutput> createMultiple(List<ItemInput> items, Integer groupId, Integer albumId) {
        Album album = getWantedAlbum(groupId, albumId);

        //Si l'on veux crée plusieurs item d'un coup
        List<ItemInput> itemInputList = new ArrayList<>();

        for (ItemInput itemInput : items) {
            Integer toAdd = itemInput.nombre;
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
     * Try to get the wanted album
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return Album
     * @Override
     */
    private Album getWantedAlbum(int groupId, int albumId) {
        Optional<Group> group = Optional.ofNullable(groupDao.get(groupId));
        Optional<Album> album = Optional.ofNullable(albumDao.getAlbumFromGroup(group.orElseThrow(NotFoundException::new), albumId));
        return album.orElseThrow(NotFoundException::new);
    }
}
