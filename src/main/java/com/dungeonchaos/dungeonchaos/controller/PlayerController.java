package com.dungeonchaos.dungeonchaos.controller;


import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

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
        Player createdPlayer = playerService.createPlayer(Long.parseLong(body.get("selectedCharacterId")));
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }
}
