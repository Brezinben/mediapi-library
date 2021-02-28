package org.stcharles.jakartatp.controllers.Album;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.api.Album.AlbumOutput;
import org.stcharles.jakartatp.dao.Album.AlbumDao;
import org.stcharles.jakartatp.dao.Group.GroupDao;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Prod
public class AlbumControllerImp implements AlbumController {
    @Inject
    @Prod
    private AlbumDao albumDao;
    @Inject
    @Prod
    private GroupDao groupDao;

    @Override
    public List<AlbumOutput> getAll(int groupId) {
        Optional<Group> group = Optional.ofNullable(groupDao.get(groupId));
        return albumDao.getAlbumsFromGroup(group.orElseThrow(NotFoundException::new))
                .stream()
                .map(AlbumOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumOutput get(int groupId, int albumId) {
        Album wantedAlbum = getWantedAlbum(groupId, albumId);
        return new AlbumOutput(wantedAlbum);
    }

    @Override
    public List<AlbumOutput> getByTitle(String title) {
        return null;
    }

    @Override
    @Transactional
    public AlbumOutput create(Integer groupId, String title, LocalDate release) {
        //Comme un groupe n'a pas 100K Album je fait le filtre sur le possible cache de this.getAll.
        boolean exist = getAll(groupId).stream().anyMatch(a -> a.title.toLowerCase(Locale.ROOT).equals(title.toLowerCase(Locale.ROOT)));
        if (exist) {
            throw new ValidationException("L'album '" + title + "' existe déjà pour ce groupe.");
        }
        Optional<Group> group = Optional.ofNullable(groupDao.get(groupId));
        Album album = new Album(group.orElseThrow(NotFoundException::new), title, release, null);
        albumDao.persist(album);
        return new AlbumOutput(album);
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
