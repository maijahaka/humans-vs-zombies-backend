package com.experis.humansvszombies.models.wrappers;
import java.time.LocalDateTime;

public class KillStatisticsWrapper {
    private String killerName;
    private String victimName;
    private LocalDateTime timeStamp;

    public String getKillerName() {
        return killerName;
    }

    public void setKillerName(String killerName) {
        this.killerName = killerName;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
