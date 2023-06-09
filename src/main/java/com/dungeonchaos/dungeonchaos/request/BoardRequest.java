package com.dungeonchaos.dungeonchaos.request;

public class BoardRequest {
    private Long playerId;

    public BoardRequest() {}
    public BoardRequest(Long playerId) {
        this.playerId = playerId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
