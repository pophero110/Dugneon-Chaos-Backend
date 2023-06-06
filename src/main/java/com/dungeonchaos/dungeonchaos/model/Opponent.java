package com.dungeonchaos.dungeonchaos.model;

import com.dungeonchaos.dungeonchaos.model.Fight.Fight;

import javax.persistence.*;

@Entity
@Table(name = "opponent")
public class Opponent extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "fight_id")
    private Fight fight;

    public Opponent(Monster monster) {
        this.setName(monster.getName());
        this.setAttack(monster.getAttack());
        this.setHealth(monster.getHealth());
        this.setDefense(monster.getDefense());
        this.setSpeed(monster.getSpeed());
    }

    public Opponent() {

    }

    public void setFight(Fight fight) {
        this.fight = fight;
    }
}
