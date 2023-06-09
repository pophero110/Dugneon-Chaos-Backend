package com.dungeonchaos.dungeonchaos.controller;


import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/players")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    @PostMapping(path = "")
    public ResponseEntity<Player> createPlayer(@RequestBody Map<String, String> body) {
        //TODO: refactor using request body class
        Player createdPlayer = playerService.createPlayer(Long.parseLong(body.get("selectedCharacterId")));
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{playerId}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long playerId) {
        Player createdPlayer = playerService.getPlayer(playerId);
        return new ResponseEntity<>(createdPlayer, HttpStatus.OK);
    }

    @GetMapping(path = "/identityKey/{identityId}")
    public ResponseEntity<Player> getPlayerByIdentity(@PathVariable String identityId) {
        Player createdPlayer = playerService.getPlayerByIdentity(identityId);
        return new ResponseEntity<>(createdPlayer, HttpStatus.OK);
    }

    @PutMapping(path = "/{playerId}/increaseDifficultyByOne")
    public ResponseEntity<Player> increasePlayerDifficultyByOne(@PathVariable Long playerId) {
        Player createdPlayer = playerService.increasePlayerDifficultyByOne(playerId);
        return new ResponseEntity<>(createdPlayer, HttpStatus.OK);
    }
}
