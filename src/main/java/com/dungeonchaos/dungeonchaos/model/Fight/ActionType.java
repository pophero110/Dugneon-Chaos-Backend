package com.dungeonchaos.dungeonchaos.model.Fight;

public enum ActionType {
    ATTACK("ATTACK"),
    DEFEND("DEFEND"),
    FLEE("FLEE");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
