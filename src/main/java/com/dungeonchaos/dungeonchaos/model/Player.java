package com.dungeonchaos.dungeonchaos.model;

import com.dungeonchaos.dungeonchaos.model.Fight.Fight;
import com.dungeonchaos.dungeonchaos.model.Reward.Reward;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "player")
public class Player extends BaseEntity {
    @OneToMany(mappedBy = "player")
    private List<Fight> fights;

    @OneToMany(mappedBy = "player")
    private List<Reward> rewards;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private Inventory inventory;

    @Column
    private int goldCoin;

    @Column
    private  int difficulty = 1;

    @Column(unique = true)
    private String identityKey;

    public Player(Character character) {
        this.setName(character.getName());
        this.setAttack(character.getAttack());
        this.setHealth(character.getHealth());
        this.setDefense(character.getDefense());
        this.setSpeed(character.getSpeed());
    }

    public Player() {

    }

    public int getGoldCoin() {
        return goldCoin;
    }

    public void addGoldCoin(int goldCoin) {
        this.goldCoin += goldCoin;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory =inventory;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void increaseDifficultyByOne() {
        this.difficulty++;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getIdentityKey() {
        return identityKey;
    }

    public void setIdentityKey(String identityKey) {
        this.identityKey = identityKey;
    }
}
