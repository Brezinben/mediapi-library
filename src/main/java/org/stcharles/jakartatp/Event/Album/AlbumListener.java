package org.stcharles.jakartatp.Event.Album;

import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.stcharles.jakartatp.model.Album;


/**
 * The class Album listener
 */
public class AlbumListener {
    @Inject
    @Any
    private Event<AlbumChanged> albumChangedEvent;

    /**
     * Après la MAJ on déclenche un event asynchrone pour mettre à jour le cache.
     *
     * @param album
     */
    @PostUpdate
    void onPostUpdate(Album album) {
        albumChangedEvent.fireAsync(new AlbumChanged());
    }

    /**
     * Après la suppression on déclenche un event asynchrone pour mettre à jour le cache.
     *
     * @param album
     */
    @PostRemove
    void onPostRemove(Album album) {
        albumChangedEvent.fireAsync(new AlbumChanged());
    }

}
