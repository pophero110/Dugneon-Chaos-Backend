package com.dungeonchaos.dungeonchaos.controller;


import com.dungeonchaos.dungeonchaos.model.Fight.Fight;
import com.dungeonchaos.dungeonchaos.model.Reward.Reward;
import com.dungeonchaos.dungeonchaos.request.FightRequest;
import com.dungeonchaos.dungeonchaos.service.FightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/fights")
public class FightController {

    private final FightService fightService;

    @Autowired
    FightController(FightService fightService) {
        this.fightService = fightService;
    }
    @PostMapping(path = "/start")
    public ResponseEntity<?> startFight(@RequestBody FightRequest fightRequest) {
        Fight fight = fightService.startFight(fightRequest.getPlayerId());
        return new ResponseEntity<>(fight, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{fightId}/playerPerformAction")
    public ResponseEntity<?> playerPerformAction(@PathVariable Long fightId, @RequestBody FightRequest fightRequest) {
        Fight fight = fightService.playerPerformAction(fightId, fightRequest.getActionType());
        return new ResponseEntity<>(fight, HttpStatus.OK);
    }


    @PutMapping(path = "/{fightId}/opponentPerformAction")
    public ResponseEntity<?> opponentPerformAction(@PathVariable Long fightId) {
        Fight fight = fightService.opponentPerformAction(fightId);
        return new ResponseEntity<>(fight, HttpStatus.OK);
    }

    @PostMapping(path = "/{fightId}/playerWinFight")
    public ResponseEntity<?> playerWinFight(@PathVariable Long fightId) {
        Reward reward = fightService.playerWinFight(fightId);
        return new ResponseEntity<>(reward, HttpStatus.CREATED);
    }
}
