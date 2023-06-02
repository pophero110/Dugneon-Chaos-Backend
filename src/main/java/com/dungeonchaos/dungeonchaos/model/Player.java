package com.dungeonchaos.dungeonchaos.model;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "player")
public class Player extends BaseEntity {


    public Player(Character character) {
        this.setName(character.getName());
        this.setAttack(character.getAttack());
        this.setHealth(character.getHealth());
        this.setDefense(character.getDefense());
        this.setSpeed(character.getSpeed());
    }

    public Player() {

    }
}
