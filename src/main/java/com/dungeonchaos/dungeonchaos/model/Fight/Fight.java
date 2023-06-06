package com.dungeonchaos.dungeonchaos.model.Fight;

import com.dungeonchaos.dungeonchaos.model.Opponent;
import com.dungeonchaos.dungeonchaos.model.Player;

import javax.persistence.*;

@Entity
@Table(name = "fight")
public class Fight {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player player;

    @OneToOne(mappedBy = "fight", cascade = CascadeType.PERSIST)
    private Opponent opponent;

    @Enumerated(EnumType.STRING)
    @Column(name = "opponent_action_type")
    private ActionType opponentActionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_action_type")
    private ActionType playerActionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_turn")
    private CurrentTurn currentTurn;

    @Enumerated(EnumType.STRING)
    @Column(name = "fight_result")
    private FightResult fightResult;


    public Fight(Player player, Opponent opponent) {
        this.player = player;
        this.opponent = opponent;
    }

    public Fight() {

    }

    public Long getId() {
        return id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setOpponent(Opponent opponent) {
        this.opponent = opponent;
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public Player getPlayer() {
        return player;
    }

    public ActionType getOpponentActionType() {
        return opponentActionType;
    }

    public void setOpponentActionType(ActionType opponentActionType) {
        this.opponentActionType = opponentActionType;
    }

    public ActionType getPlayerActionType() {
        return playerActionType;
    }

    public void setPlayerActionType(ActionType playerActionType) {
        this.playerActionType = playerActionType;
    }

    public FightResult getFightResult() {
        return fightResult;
    }

    public void setFightResult(FightResult fightResult) {
        this.fightResult = fightResult;
    }

    public CurrentTurn getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(CurrentTurn currentTurn) {
        this.currentTurn = currentTurn;
    }
}
