package com.dungeonchaos.dungeonchaos.controller;


import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/characters")
public class CharacterController {

    private CharacterService characterService;

    @Autowired
    CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Character> getCharacters() {
        return this.characterService.getCharacters();
    }
}
