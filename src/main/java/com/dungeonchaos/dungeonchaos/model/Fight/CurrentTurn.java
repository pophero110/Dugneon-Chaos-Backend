package com.dungeonchaos.dungeonchaos.model.Fight;

public enum  CurrentTurn {
    PLAYER("PLAYER"),
    OPPONENT("OPPONENT");

    private final String value;

    CurrentTurn(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
