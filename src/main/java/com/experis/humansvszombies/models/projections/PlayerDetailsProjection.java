package com.experis.humansvszombies.models.projections;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Kill;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.stream.Collectors;

/*
* Dynamic projection interface.
*
* Restricts non-admin users access to players private fields such as isHuman boolean and JWT subject_id String
 */
public interface PlayerDetailsProjection {

    String getPlayerName();
    String getIsHuman();

    @JsonIgnore
    Game getGame();
    @JsonIgnore
    List<Kill> getKills();
    @JsonIgnore
    Kill getVictimOf();

    @JsonGetter("game")
    default String getPlayingIn(){
        return "/api/v1/games/" + getGame().getId() + "/";
    }

    @JsonGetter("victimOf")
    default String getKilledBy(){
        if (getVictimOf() == null)
            return null;
        return "/api/v1/games/" + getVictimOf().getKiller().getGame().getId() + "/players/" + getVictimOf().getKiller().getId();
    }
    @JsonGetter("kills")
    default List<String> getPlayersKills(){
        return getKills().stream()
                .map(kill -> "/api/v1/games/" + this.getGame().getId() + "/players/" + kill.getVictim().getId())
                .collect(Collectors.toList());
    }
}
