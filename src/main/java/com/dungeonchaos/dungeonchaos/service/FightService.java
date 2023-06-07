package com.dungeonchaos.dungeonchaos.service;


import com.dungeonchaos.dungeonchaos.combat.Combat;
import com.dungeonchaos.dungeonchaos.exception.InformationInvalidException;
import com.dungeonchaos.dungeonchaos.exception.InformationNotFoundException;
import com.dungeonchaos.dungeonchaos.model.Fight.ActionType;
import com.dungeonchaos.dungeonchaos.model.Fight.CurrentTurn;
import com.dungeonchaos.dungeonchaos.model.Fight.Fight;
import com.dungeonchaos.dungeonchaos.model.Fight.FightResult;
import com.dungeonchaos.dungeonchaos.model.Monster;
import com.dungeonchaos.dungeonchaos.model.Opponent;
import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.model.Reward.Reward;
import com.dungeonchaos.dungeonchaos.model.Reward.RewardType;
import com.dungeonchaos.dungeonchaos.repository.FightRepository;
import com.dungeonchaos.dungeonchaos.repository.MonsterRepository;
import com.dungeonchaos.dungeonchaos.repository.OpponentRepository;
import com.dungeonchaos.dungeonchaos.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FightService {
    private final FightRepository fightRepository;
    private final MonsterRepository monsterRepository;
    private final OpponentRepository opponentRepository;
    private final PlayerRepository playerRepository;
    private final RewardService rewardService;

    @Autowired
    FightService(FightRepository fightRepository, MonsterRepository monsterRepository, OpponentRepository opponentRepository, PlayerRepository playerRepository, RewardService rewardService) {
        this.fightRepository = fightRepository;
        this.monsterRepository = monsterRepository;
        this.opponentRepository = opponentRepository;
        this.playerRepository = playerRepository;
        this.rewardService = rewardService;
    }

    public Fight startFight(Long playerId, Long monsterId) {
        Monster monster = monsterRepository.findById(monsterId).orElseThrow(() -> new InformationNotFoundException("Monster is not found with id " + monsterId));
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new InformationNotFoundException("Player is not found with id " + playerId));
        Opponent opponent = new Opponent(monster);
        Fight fight = new Fight(player, opponent);
        opponent.setFight(fight);
        fight.setFightResult(FightResult.ONGOING);
        if (opponent.getSpeed() > player.getSpeed()) {
            fight.setCurrentTurn(CurrentTurn.OPPONENT);
        } else {
            fight.setCurrentTurn(CurrentTurn.PLAYER);
        }

        return fightRepository.save(fight);
    }

    public Fight playerPerformAction(Long fightId, String actionType) {
        Fight fight = fightRepository.findById(fightId).orElseThrow(() -> new InformationNotFoundException("Fight is not found with id " + fightId));
        Player player = fight.getPlayer();
        Opponent opponent = fight.getOpponent();
        FightResult fightResult = FightResult.ONGOING;

        if (ActionType.ATTACK.getValue().equals(actionType)) {
            fightResult = Combat.performAttack(player, opponent);
        } else if (ActionType.DEFEND.getValue().equals(actionType)) {
        } else if (ActionType.FLEE.getValue().equals(actionType)) {
            fightResult = Combat.performFlee(player, opponent);
        } else {
            throw new InformationInvalidException("Action type: " + actionType + " is not found: ");
        }

        fight.setFightResult(fightResult);
        fight.setPlayerActionType(ActionType.valueOf(actionType));
        fight.setCurrentTurn(CurrentTurn.OPPONENT);
        return fightRepository.save(fight);
    }

    public Fight opponentPerformAction(Long fightId) {
        Fight fight = fightRepository.findById(fightId).orElseThrow(() -> new InformationNotFoundException("Fight is not found with id " + fightId));
        Player player = fight.getPlayer();
        Opponent opponent = fight.getOpponent();
        FightResult fightResult = FightResult.ONGOING;
        // opponent attack player
        fight.setOpponentActionType(ActionType.ATTACK);

        // player chose defend
        if (fight.getPlayerActionType() != null && fight.getPlayerActionType().equals(ActionType.DEFEND)) {
            fightResult = Combat.performDefend(player, opponent);
        } else {
            fightResult = Combat.performAttack(opponent, player);
        }

        fight.setFightResult(fightResult);
        fight.setCurrentTurn(CurrentTurn.PLAYER);
        return fightRepository.save(fight);
    }

    public Reward playerWinFight(Long fightId) {
        Fight fight = fightRepository.findById(fightId).orElseThrow(() -> new InformationNotFoundException("Fight is not found with id " + fightId));
        if (fight.getFightResult().equals(FightResult.VICTORY_PLAYER)) {
            Player player = fight.getPlayer();
            Opponent opponent = fight.getOpponent();
            return rewardService.createReward(RewardType.GOLD_COIN, player, opponent);
        }
        throw new InformationInvalidException("You don't win the fight!");
    }
}
