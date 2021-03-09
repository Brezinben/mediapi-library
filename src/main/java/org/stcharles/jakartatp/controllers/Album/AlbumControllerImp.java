package org.stcharles.jakartatp.controllers.Album;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.controllers.FuzzyFinding.FuzzyFinding;
import org.stcharles.jakartatp.controllers.Group.GroupController;
import org.stcharles.jakartatp.dao.Album.AlbumDao;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Logger;


/**
 * The class Album controller imp implements album controller
 */
@Prod
@ApplicationScoped
public class AlbumControllerImp implements AlbumController {
    @Inject
    @Prod
    private AlbumDao albumDao;

    @Inject
    @Prod
    private GroupController groupController;

    @Inject
    @Prod
    private FuzzyFinding fuzzyFinding;


    /**
     * Gets the all
     *
     * @param groupId the group identifier
     * @return the all
     * @Override
     */
    public List<Album> getAll(Integer groupId) {
        Group group = groupController.get(groupId);
        return Optional.ofNullable(group.getAlbums()).orElseThrow(NotFoundException::new);
    }


    /**
     * Try to get the wanted album
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return Album
     * @Override
     **/
    public Album get(Integer groupId, Integer albumId) {

        Group group = groupController.get(groupId);
        List<Album> albums = Optional.ofNullable(group.getAlbums()).orElseThrow(NotFoundException::new);
        return albums.stream()
                .filter(album -> album.getId().equals(albumId))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }


    /**
     * Gets the by title
     *
     * @param groupId the group identifier
     * @param title   the title
     * @return the by title
     * @Override
     */
    public List<Album> getByTitle(Integer groupId, String title) {
        return Optional.ofNullable(fuzzyFinding.getAlbumByTitle(groupId, title)).orElseThrow(NotFoundException::new);
    }


    /**
     * Create
     *
     * @param groupId the group identifier
     * @param title   the title
     * @param release the release
     * @return Album
     * @Override
     */

    @Transactional
    public Album create(Integer groupId, String title, LocalDate release) {
        albumValidation(groupId, title);
        Group group = groupController.get(groupId);
        Album album = new Album(group, title, release, null);
        albumDao.persist(album);
        return album;
    }


    /**
     * Album validation
     *
     * @param groupId the group identifier
     * @param title   the title
     */
    private void albumValidation(Integer groupId, String title) {
        //Comme un groupe n'a pas 100K Album je fait le filtre sur le possible cache de this.getAll.
        boolean exist = getAll(groupId)
                .stream()
                .anyMatch(a -> a.getTitle().toLowerCase(Locale.ROOT).equals(title.toLowerCase(Locale.ROOT)));

        if (exist) {
            throw new ValidationException("L'album '" + title + "' existe déjà pour ce groupe.");
        }
    }


    /**
     * Update
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @param title   the title
     * @param release the release
     * @return Album
     * @Override
     */
    @Transactional
    public Album update(Integer groupId, Integer albumId, String title, LocalDate release) {

        Album album = get(groupId, albumId);
        albumValidation(groupId, title);
        album.setRelease(release);
        album.setTitle(title);
        return album;
    }


    /**
     * Remove
     *
     * @param groupId the group identifier
     * @param albumId the album identifier
     * @return Boolean
     * @Override
     **/
    @Transactional
    public Boolean remove(Integer groupId, Integer albumId) {

        Album album = get(groupId, albumId);
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


}
