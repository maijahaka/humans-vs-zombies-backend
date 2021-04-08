package com.experis.humansvszombies.models.projections;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Kill;
import com.experis.humansvszombies.models.Player;
import java.util.List;

//todo: add user projections
public interface NonAdminUserProjection {
    String getPlayerName();
    Player getVictimOf();
    List<Kill> getKills();
    boolean getIsHuman();
    Game getGet();
}
