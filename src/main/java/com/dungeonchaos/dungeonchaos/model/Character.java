package com.dungeonchaos.dungeonchaos.model;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name= "character")
public class Character extends BaseEntity {

    public Character(String name, int health, int attack, int defense, int speed) {
        super(name, health, attack, defense, speed);
    }

    public Character() {
        super();
    }
}
