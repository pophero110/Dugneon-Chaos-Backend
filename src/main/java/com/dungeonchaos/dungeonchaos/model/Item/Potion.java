package com.dungeonchaos.dungeonchaos.model.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("potion")
public class Potion extends Item {
    @Enumerated(EnumType.STRING)
    private PotionType potionType;

    private String potionEffect;
    private Integer potionDuration;

    public Potion(Item item, Potion potion) {
        super(item);
        this.potionType = potion.getPotionType();
        this.potionEffect = potion.getPotionEffect();
        this.potionDuration = potion.getPotionDuration();
    }

    public Potion() {}

    public PotionType getPotionType() {
        return potionType;
    }

    public void setPotionType(PotionType potionType) {
        this.potionType = potionType;
    }

    public String getPotionEffect() {
        return potionEffect;
    }

    public void setPotionEffect(String potionEffect) {
        this.potionEffect = potionEffect;
    }

    public Integer getPotionDuration() {
        return potionDuration;
    }

    public void setPotionDuration(Integer potionDuration) {
        this.potionDuration = potionDuration;
    }
}
