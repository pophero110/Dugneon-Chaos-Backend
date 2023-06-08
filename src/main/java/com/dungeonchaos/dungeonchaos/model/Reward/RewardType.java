package com.dungeonchaos.dungeonchaos.model.Reward;

public enum RewardType {
    GOLD_COIN("GOLD_COIN"),
    ITEM("ITEM");

    private final String value;

    RewardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
