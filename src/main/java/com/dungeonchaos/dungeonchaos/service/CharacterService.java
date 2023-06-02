package com.dungeonchaos.dungeonchaos.service;


import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> getCharacters() {
        return this.characterRepository.findAll();
    }
}
