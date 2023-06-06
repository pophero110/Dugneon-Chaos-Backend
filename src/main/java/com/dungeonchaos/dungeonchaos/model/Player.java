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

//    @OneToMany(mappedBy = "player")
//    private List<Reward> rewards;

    @Column
    private int goldCoin;

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
}
