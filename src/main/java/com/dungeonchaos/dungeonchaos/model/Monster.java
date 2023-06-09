package com.dungeonchaos.dungeonchaos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "monster")
public class Monster extends BaseEntity {

    @Column
    private int diffculty;

    public Monster(String name, int health, int attack, int defense, int speed, int diffculty) {
        super(name, health, attack, defense, speed);
        this.diffculty= diffculty;
    }

    public Monster() {

    }


    public int getDiffculty() {
        return diffculty;
    }
}
