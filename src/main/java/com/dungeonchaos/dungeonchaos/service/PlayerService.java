package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.exception.InformationNotFoundException;
import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.repository.CharacterRepository;
import com.dungeonchaos.dungeonchaos.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    private CharacterRepository characterRepository;

    @Autowired
    PlayerService(PlayerRepository playerRepository, CharacterRepository characterRepository) {
        this.playerRepository = playerRepository;
        this.characterRepository = characterRepository;
    }

    public Player createPlayer(Long selectedCharacterId) {
        Character character = characterRepository.findById(selectedCharacterId).orElseThrow(
                () -> new InformationNotFoundException("Character is not found with id: " + selectedCharacterId)
        );
        Player createdPlayer = new Player(character);
         playerRepository.save(createdPlayer);
        return createdPlayer;
    }
}
