package com.dungeonchaos.dungeonchaos.combat;

import com.dungeonchaos.dungeonchaos.model.BaseEntity;
import com.dungeonchaos.dungeonchaos.model.Fight.FightResult;
import com.dungeonchaos.dungeonchaos.model.Player;

import java.util.Random;

public class Combat {

    /**
     * Performs an attack action in a fight between two entities.
     * @param performer The entity performing the attack.
     * @param target The entity being attacked.
     * @return The result of the attack.
     */
    public static FightResult performAttack(BaseEntity performer, BaseEntity target) {
        applyDamage(target,performer.getAttack() - target.getDefense());
        if (target.getHealth() <= 0) {
            if (performer instanceof Player) {
                return FightResult.VICTORY_PLAYER;
            } else {
                return FightResult.VICTORY_OPPONENT;
            }
        }
        return FightResult.ONGOING;
    }

    /**
     * Performs a flee action in a fight between two entities.
     * @param performer The entity attempting to flee.
     * @param target The entity being fled from.
     * @return The result of the flee action.
     */
    public static FightResult performFlee(BaseEntity performer, BaseEntity target) {
        int performerSpeed = performer.getSpeed();
        int targetSpeed = target.getSpeed();
        double fleeRate = (double) (performerSpeed - targetSpeed) / performerSpeed;

        // a random value between 0 (inclusive) and 1 (exclusive).
        Random random = new Random();
        double randomValue = random.nextDouble();

        if (randomValue < fleeRate) {
            return (performer instanceof Player) ? FightResult.FLEE_PLAYER : FightResult.FLEE_MONSTER;
        } else {
            return FightResult.ONGOING;
        }
    }

    /**
     * Performs a defend action in a fight between two entities.
     * @param performer The entity performing the defense.
     * @param target The entity being defended.
     * @return The result of the defend action.
     */
    public static FightResult performDefend(BaseEntity performer, BaseEntity target) {
        int performerDefense = performer.getDefense();
        int targetAttack = target.getAttack();

        int damage = targetAttack - performerDefense;
        if (damage > 0) {
            applyDamage(target, damage);
            if (target.getHealth() <= 0) {
                return (performer instanceof Player) ? FightResult.VICTORY_PLAYER : FightResult.VICTORY_OPPONENT;
            }
        }
        return FightResult.ONGOING;
    }

    /**
     * Applies damage to a target entity.
     * @param target The entity to apply the damage to.
     * @param damage The amount of damage to apply.
     */
    private static void applyDamage(BaseEntity target, int damage) {
        if(damage <= 0) {
            return;
        }
        int currentHealth = target.getHealth();
        int newHealth = currentHealth - damage;
        target.setHealth(newHealth);
    }
}