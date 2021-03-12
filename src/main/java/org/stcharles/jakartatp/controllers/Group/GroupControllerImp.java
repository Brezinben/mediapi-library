package org.stcharles.jakartatp.controllers.Group;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.controllers.FuzzyFinding.FuzzyFinding;
import org.stcharles.jakartatp.dao.Group.GroupDao;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


/**
 * The class Group controller imp implements group controller
 */
@Prod
@ApplicationScoped
public class GroupControllerImp implements GroupController {
    @Inject
    @Prod
    private GroupDao groupDao;

    @Inject
    @Prod
    private FuzzyFinding fuzzyFinding;

    /**
     * @param id the id
     * @return Group
     * @Override Gets the
     */

    public Group get(Integer id) {
        return Optional.ofNullable(groupDao.get(id)).orElseThrow(NotFoundException::new);
    }

    /**
     * @param name the name to find
     * @return List<Group>
     * @Override
     */

    public List<Group> getByName(String name) {

        return fuzzyFinding.getGroupsByName(name);
    }


    /**
     * Update
     *
     * @param groupId   the group identifier
     * @param name      the name
     * @param createdAt the created at
     * @return Group
     * @Override
     */
    @Transactional
    public Group update(Integer groupId, String name, LocalDate createdAt) {

        Group group = Optional.ofNullable(groupDao.get(groupId)).orElseThrow(NotFoundException::new);
        group.setCreated_at(createdAt);
        group.setName(name);
        return group;
    }


    /**
     * Remove
     *
     * @param groupId the group identifier
     * @return Boolean
     * @Override
     */
    @Transactional
    public Boolean remove(Integer groupId) {

        Group group = Optional.ofNullable(groupDao.get(groupId)).orElseThrow(NotFoundException::new);
        if (group.getAlbums().size() > 0) {
            throw new ValidationException("Il y a des albums liée a ce groupe veillez les supprimer avant.");
        }
        try {
            groupDao.remove(group);
            return true;
        } catch (Exception exception) {
            Logger.getAnonymousLogger(exception.getMessage());
            return false;
        }
    }

    /**
     * @return List<Group>
     * @Override Gets the all
     */

    public List<Group> getAll() {
        return groupDao.getAll();
    }


    /**
     * @param name      the name
     * @param createdAt the created_at
     * @return Group
     * @Override Create
     */
    @Transactional
    public Group create(String name, LocalDate createdAt) {

        Group newGroup = new Group(name, createdAt);
        //On pourrait faire une vérification avec un equal sur la date et le nom
        Optional<List<Group>> groups = Optional.ofNullable(getByName(name));
        if (groups.isPresent()) {
            boolean alreadyExist = groups.get().stream().anyMatch(group -> group.getCreated_at().equals(createdAt) && group.getName().equals(name));
            if (alreadyExist) {
                throw new ValidationException("Le groupe existe déjà");
            }
        }
        groupDao.persist(newGroup);
        return newGroup;
    }

}
