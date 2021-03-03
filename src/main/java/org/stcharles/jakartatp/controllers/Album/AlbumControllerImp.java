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
import java.util.logging.Logger;
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
    public List<AlbumOutput> getAll(Integer groupId) {
        Group group = Optional.ofNullable(groupDao.get(groupId)).orElseThrow(NotFoundException::new);
        List<Album> albums = Optional.ofNullable(group.getAlbums()).orElseThrow(NotFoundException::new);
        return albums.stream()
                .map(AlbumOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumOutput get(Integer groupId, Integer albumId) {
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
        albumValidation(groupId, title);
        Optional<Group> group = Optional.ofNullable(groupDao.get(groupId));
        Album album = new Album(group.orElseThrow(NotFoundException::new), title, release, null);
        albumDao.persist(album);
        return new AlbumOutput(album);
    }

    private void albumValidation(Integer groupId, String title) {
        //Comme un groupe n'a pas 100K Album je fait le filtre sur le possible cache de this.getAll.
        boolean exist = getAll(groupId).stream().anyMatch(a -> a.title.toLowerCase(Locale.ROOT).equals(title.toLowerCase(Locale.ROOT)));
        if (exist) {
            throw new ValidationException("L'album '" + title + "' existe déjà pour ce groupe.");
        }
    }


    @Override
    @Transactional
    public AlbumOutput update(Integer groupId, Integer albumId, String title, LocalDate release) {
        Album album = getWantedAlbum(groupId, albumId);
        albumValidation(groupId, title);
        album.setRelease(release);
        album.setTitle(title);
        return new AlbumOutput(album);
    }

    @Override
    @Transactional
    public Boolean remove(Integer groupId, Integer albumId) {
        Album album = getWantedAlbum(groupId, albumId);
        if (album.getItems().size() > 0) {
            throw new ValidationException("Il y a des items liée a cette album veillez les supprimer avant.");
        }

        try {
            albumDao.remove(album);
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

}
