package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.exception.InformationNotFoundException;
import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.model.Inventory;
import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.repository.CharacterRepository;
import com.dungeonchaos.dungeonchaos.repository.InventoryRepository;
import com.dungeonchaos.dungeonchaos.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    private CharacterRepository characterRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    PlayerService(PlayerRepository playerRepository, CharacterRepository characterRepository, InventoryRepository inventoryRepository) {
        this.playerRepository = playerRepository;
        this.characterRepository = characterRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public Player createPlayer(Long selectedCharacterId) {
        Character character = characterRepository.findById(selectedCharacterId).orElseThrow(
                () -> new InformationNotFoundException("Character is not found with id: " + selectedCharacterId)
        );
        Player createdPlayer = new Player(character);
        Inventory inventory = new Inventory();
        createdPlayer.setInventory(inventory);
        inventory.setPlayer(createdPlayer);
        playerRepository.save(createdPlayer);
        return createdPlayer;
    }

    public Player getPlayer(Long playerId) {
        Player player = this.playerRepository.findById(playerId).orElseThrow(() -> new InformationNotFoundException("Player is not found with id " + playerId));
        return player;
    }
}
