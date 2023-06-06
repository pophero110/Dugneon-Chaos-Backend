package com.dungeonchaos.dungeonchaos.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "monster")
public class Monster extends BaseEntity {
    public Monster(String name, int health, int attack, int defense, int speed) {
        super(name, health, attack, defense, speed);
    }

    public Monster() {

    }
}
