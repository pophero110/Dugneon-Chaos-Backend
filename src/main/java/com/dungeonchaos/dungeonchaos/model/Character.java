package com.dungeonchaos.dungeonchaos.model;

import javax.persistence.*;

@Entity
@Table(name= "character")
public class Character {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int health;

    @Column
    private int attack;

    @Column
    private int defense;

    @Column
    private int speed;

    public Character(String name, int health, int attack, int defense, int speed) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }
}
