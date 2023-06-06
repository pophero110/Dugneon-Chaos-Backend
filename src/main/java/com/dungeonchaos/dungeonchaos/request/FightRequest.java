package com.dungeonchaos.dungeonchaos.request;

import com.dungeonchaos.dungeonchaos.model.Opponent;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class FightRequest {
    private Long playerId;
    private Long opponentId;

    private Long monsterId;

    private String actionType;

    @OneToOne(mappedBy = "fight")
    private Opponent opponent;

    public FightRequest(Long playerId, Long monsterId) {
        this.playerId = playerId;
        this.monsterId = monsterId;
    }

    public FightRequest() {}

    public Long getPlayerId() {
        return playerId;
    }

    public Long getOpponentId() {
        return opponentId;
    }

    public Long getMonsterId() {
        return monsterId;
    }

    public String getActionType() {
        return actionType;
    }
}
