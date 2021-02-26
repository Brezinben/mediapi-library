package org.stcharles.jakartatp.controllers.Group;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.api.Album.AlbumOutput;
import org.stcharles.jakartatp.api.Group.GroupOutput;
import org.stcharles.jakartatp.api.Item.ItemOutput;
import org.stcharles.jakartatp.dao.Album.AlbumDao;
import org.stcharles.jakartatp.dao.Group.GroupDao;
import org.stcharles.jakartatp.dao.Item.ItemDao;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Prod
public class GroupControllerImp implements GroupController {
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
    public List<GroupOutput> getAll() {
        return groupDao.getAll()
                .stream()
                .map(GroupOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public GroupOutput get(int id) {
        return Optional.ofNullable(groupDao.get(id))
                .map(GroupOutput::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<AlbumOutput> getAlbums(int id) {
        Group group = groupDao.get(id);
        return albumDao.getAlbumsFromGroup(group)
                .stream()
                .map(AlbumOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumOutput getOneAlbum(int groupId, int albumId) {
        Album wantedAlbum = getWantedAlbum(groupId, albumId);
        return new AlbumOutput(wantedAlbum);
    }

    private Album getWantedAlbum(int groupId, int albumId) {
        Group g = groupDao.get(groupId);
        return albumDao.getAlbumFromGroup(g, albumId);
    }

    @Override
    public Group create(List<Album> albums, String name, LocalDate created_at) {
        return null;
    }

    @Override
    public List<ItemOutput> getItems(int groupId, int albumId) {
        Album album = this.getWantedAlbum(groupId, albumId);
        return itemDao.getAllFromAlbum(album)
                .stream()
                .map(ItemOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public ItemOutput getOneItem(int groupId, int albumId, int itemId) {
        return null;
    }
}
