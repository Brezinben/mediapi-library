package org.stcharles.jakartatp.controllers.FuzzyFinding;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.stcharles.jakartatp.Event.Album.AlbumChanged;
import org.stcharles.jakartatp.Event.Group.GroupChanged;
import org.stcharles.jakartatp.dao.Album.AlbumDao;
import org.stcharles.jakartatp.dao.Group.GroupDao;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The class Fuzzy finding imp implements fuzzy finding
 */
@Prod
@ApplicationScoped
public class FuzzyFindingImp implements FuzzyFinding {
    private Map<Integer, String> cacheAlbumTitle;
    private Map<Integer, String> cacheGroupName;

    @Inject
    @Prod
    private AlbumDao albumDao;

    @Inject
    @Prod
    private GroupDao groupDao;


    /**
     * Scored match
     *
     * @param query     the query
     * @param candidate the candidate
     * @return Integer
     */
    private static Integer scoredMatch(String query, String candidate) {
        query = query.toLowerCase();
        candidate = candidate.toLowerCase();

        if (query.length() == 0 || candidate.equals(query)) {
            return (candidate.length() * (candidate.length() + 1)) / 2;
        }
        if (candidate.length() < query.length()) {
            return 0;
        }

        List<Character> charsToBeFound = new ArrayList();
        for (char character : query.toCharArray()) {
            charsToBeFound.add(character);
        }

        int bonusFactor = 1;
        int score = 0;

        for (char candidateChar : candidate.toCharArray()) {
            if (charsToBeFound.size() == 0) {
                break;
            }

            if (candidateChar == charsToBeFound.get(0)) {
                charsToBeFound.remove(0);
                score += bonusFactor;
                bonusFactor += 1;
            } else {
                bonusFactor = 1;
            }
        }

        return charsToBeFound.size() == 0 ? score : 0;
    }


    /**
     * Init
     */
    @PostConstruct
    public void init() {
        this.cacheGroupName = groupDao.getAll().stream().collect(Collectors.toMap(Group::getId, Group::getName));
        this.cacheAlbumTitle = albumDao.getAll().stream().collect(Collectors.toMap(Album::getId, Album::getTitle));
    }


    /**
     * Gets the album by title
     *
     * @param groupId the group identifier
     * @param query   the query
     * @return the album by title
     * @Override
     */
    public List<Album> getAlbumByTitle(Integer groupId, String query) {
        return getScoredMap(this.cacheAlbumTitle, query)
                .keySet()
                .stream()
                .map(albumDao::get)
                .collect(Collectors.toList());
    }


    /**
     * Gets the groups by name
     *
     * @param query the query
     * @return the groups by name
     * @Override
     */
    public List<Group> getGroupsByName(String query) {
        return getScoredMap(this.cacheGroupName, query)
                .keySet()
                .stream()
                .map(groupDao::get)
                .collect(Collectors.toList());
    }


    /**
     * Update groups
     *
     * @param GroupChanged the group changed
     * @Override
     */
    public void updateGroups(@ObservesAsync GroupChanged GroupChanged) {
        groupDao.refresh();
        this.cacheGroupName = groupDao.getAll().stream().collect(Collectors.toMap(Group::getId, Group::getName));
    }


    /**
     * Update albums
     *
     * @param AlbumChanged the album changed
     * @Override
     */
    public void updateAlbums(@ObservesAsync AlbumChanged AlbumChanged) {
        albumDao.refresh();
        this.cacheAlbumTitle = albumDao.getAll().stream().collect(Collectors.toMap(Album::getId, Album::getTitle));
    }

    /**
     * Gets the scored map
     *
     * @param map   the map
     * @param query the query
     * @return the scored map
     */
    @NotNull
    private Map<Integer, Integer> getScoredMap(Map<Integer, String> map, String query) {
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> scoredMatch(query, x.getValue())))
                .entrySet()
                .stream()
                .filter(x -> x.getValue() > 0)
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}
