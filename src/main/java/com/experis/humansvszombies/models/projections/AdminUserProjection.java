package com.experis.humansvszombies.models.projections;

import com.experis.humansvszombies.models.Player;
//todo: add admin projections
public interface AdminUserProjection {
    String getPlayerName();
    Player getVictimOf();
    boolean getIsPatientZero();
}
