package com.dungeonchaos.dungeonchaos.model.Reward;

public enum RewardType {
    GOLD("GOLD");

    private final String value;

    RewardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
