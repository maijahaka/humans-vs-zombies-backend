package com.experis.humansvszombies.models.stomp;

// websocket message model with relevant fields for kills
public class KillStompMessage extends StompMessage {
    private Long victimId;
    private String killerName;
    private String story;

    public KillStompMessage(
            Long gameId, StompMessageType type, Long victimId, String killerName, String story
    ) {
        super(gameId, type);
        this.victimId = victimId;
        this.killerName = killerName;
        this.story = story;
    }

    public Long getVictimId() {
        return victimId;
    }

    public void setVictimId(Long victimId) {
        this.victimId = victimId;
    }

    public String getKillerName() {
        return killerName;
    }

    public void setKillerName(String killerName) {
        this.killerName = killerName;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
