package com.experis.humansvszombies.models.stomp;

// basic websocket message model
public class StompMessage {
    private Long gameId;
    private StompMessageType type;

    public StompMessage(Long gameId, StompMessageType type) {
        this.gameId = gameId;
        this.type = type;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public StompMessageType getType() {
        return type;
    }

    public void setType(StompMessageType type) {
        this.type = type;
    }
}
