package org.stcharles.jakartatp.controllers.FuzzyFinding;


import jakarta.inject.Inject;
import org.stcharles.jakartatp.dao.Album.AlbumDao;
import org.stcharles.jakartatp.dao.Group.GroupDao;
import org.stcharles.jakartatp.model.Album;
import org.stcharles.jakartatp.model.Group;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Prod
public class FuzzyFindingImp implements FuzzyFinding {
    private final Map<Integer, String> cacheAlbumTitle;
    private final Map<Integer, String> cacheGroupName;

    @Inject
    @Prod
    private AlbumDao albumDao;

    @Inject
    @Prod
    private GroupDao groupDao;

    protected FuzzyFindingImp() {
        this.cacheGroupName = groupDao.getAll().stream().collect(Collectors.toMap(Group::getId, Group::getName));
        this.cacheAlbumTitle = albumDao.getAll().stream().collect(Collectors.toMap(Album::getId, Album::getTitle));
    }

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

    @Override

    public List<Album> getAlbumByTitle(Integer groupId, String query) {
        return cacheAlbumTitle.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> String.valueOf(scoredMatch(query, x.getValue()))))
                .entrySet()
                .stream()
                .filter(x -> Integer.parseInt(x.getValue()) > 0)
                .sorted((o1, o2) -> Integer.parseInt(o1.getValue()) - Integer.parseInt(o2.getValue()))
                .map(x -> albumDao.get(x.getKey()))
                .filter(album -> album.getGroup().getId().equals(groupId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupsByName(String query) {
        return cacheGroupName.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> String.valueOf(scoredMatch(query, x.getValue()))))
                .entrySet()
                .stream()
                .filter(x -> Integer.parseInt(x.getValue()) > 0)
                .sorted((o1, o2) -> Integer.parseInt(o1.getValue()) - Integer.parseInt(o2.getValue()))
                .map(x -> groupDao.get(x.getKey()))
                .collect(Collectors.toList());
    }
}
