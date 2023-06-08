package com.dungeonchaos.dungeonchaos.request;

public class RewardRequest {
    private Long playerId;

    private String rewardType;
    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }
}
