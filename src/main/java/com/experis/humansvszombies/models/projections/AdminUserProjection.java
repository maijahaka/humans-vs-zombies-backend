package com.experis.humansvszombies.models.projections;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Kill;

import java.util.List;

//todo: add admin projections
public interface AdminUserProjection {
    String getUserId();
    String getId();
    boolean getIsPatientZero();
    String getBiteCode();
    List<Kill> getKills();
    boolean getIsHuman();
    Game getGame();
}
