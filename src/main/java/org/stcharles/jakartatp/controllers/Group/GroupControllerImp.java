package org.stcharles.jakartatp.controllers.Group;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.api.Album.AlbumInput;
import org.stcharles.jakartatp.api.Group.GroupOutput;
import org.stcharles.jakartatp.dao.Album.AlbumDao;
import org.stcharles.jakartatp.dao.Group.GroupDao;
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


    /**
     * @param id the id
     * @return GroupOutput
     * @Override Gets the
     */

    public GroupOutput get(Integer id) {

        return Optional.ofNullable(groupDao.get(id))
                .map(GroupOutput::new)
                .orElseThrow(NotFoundException::new);
    }

    /**
     * @param name the name to find
     * @return List<GroupOutput>
     */
    @Override
    public List<GroupOutput> getByName(String name) {
        return null;
    }

    @Override
    @Transactional
    public GroupOutput update(Integer groupId, String name, LocalDate createdAt) {
        Group group = Optional.ofNullable(groupDao.get(groupId)).orElseThrow(NotFoundException::new);
        group.setCreated_at(createdAt);
        group.setName(name);
        return new GroupOutput(group);
    }

    /**
     * @return List<GroupOutput>
     * @Override Gets the all
     */

    public List<GroupOutput> getAll() {

        return groupDao.getAll()
                .stream()
                .map(GroupOutput::new)
                .collect(Collectors.toList());
    }


    /**
     * @param albums    the albums
     * @param name      the name
     * @param createdAt the created_at
     * @return Group
     * @Override Create
     */
    @Transactional
    public GroupOutput create(List<AlbumInput> albums, String name, LocalDate createdAt) {
        Group group = new Group(name, createdAt);
        groupDao.persist(group);
        albums.forEach(album -> {
            Album a = new Album(group, album.title, album.release, null);
            albumDao.persist(a);
        });
        return new GroupOutput(group);
    }

}
