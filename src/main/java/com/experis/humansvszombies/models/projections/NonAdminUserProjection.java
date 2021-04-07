package com.experis.humansvszombies.models.projections;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Kill;

import java.util.List;
//todo: add user projections
public interface NonAdminUserProjection {
    String getPlayerName();
    Game getGame();
    List<Kill> getKills();
}
