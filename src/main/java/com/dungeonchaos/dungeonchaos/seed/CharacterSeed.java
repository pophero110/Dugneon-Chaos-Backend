package com.dungeonchaos.dungeonchaos.seed;

import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CharacterSeed implements ApplicationRunner {
    private final CharacterRepository characterRepository;
    @Autowired
    CharacterSeed(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Character warrior = new Character("Warrior", 90, 10, 20, 30);
        Character rogue = new Character("Rogue", 80, 15, 10, 40);

        characterRepository.save(warrior);
        characterRepository.save(rogue);
    }
}
