package org.stcharles.jakartatp.Event.Group;

import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.stcharles.jakartatp.model.Group;

/**
 * The class Group listener
 */
public class GroupListener {
    @Inject
    @Any
    private Event<GroupChanged> groupChangedEvent;

    /**
     * Après la MAJ on déclenche un event asynchrone pour mettre à jour le cache.
     *
     * @param group
     */
    @PostUpdate
    void onPostUpdate(Group group) {
        groupChangedEvent.fireAsync(new GroupChanged());
    }

    /**
     * Après la suppression on déclenche un event asynchrone pour mettre à jour le cache.
     *
     * @param group
     */
    @PostRemove
    void onPostRemove(Group group) {
        groupChangedEvent.fireAsync(new GroupChanged());
    }

}
