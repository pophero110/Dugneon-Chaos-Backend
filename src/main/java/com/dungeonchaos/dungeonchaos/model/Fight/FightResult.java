package com.dungeonchaos.dungeonchaos.model.Fight;

public enum FightResult {
    VICTORY_PLAYER("VICTORY_PLAYER"),
    VICTORY_OPPONENT("VICTORY_OPPONENT"),
    FLEE_PLAYER("FLEE_PLAYER"),
    FLEE_MONSTER("FLEE_MONSTER"),
    ONGOING("ONGOING");

    private final String value;

    FightResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}