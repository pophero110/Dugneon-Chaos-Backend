package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.exception.InformationInvalidException;
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

    /**
     * Creates a player with the specified selected character ID.
     * @param selectedCharacterId The ID of the selected character.
     * @return The created player.
     * @throws InformationNotFoundException if the character is not found.
     */
    public Player createPlayer(Long selectedCharacterId) {
        Character character = characterRepository.findById(selectedCharacterId).orElseThrow(
                () -> new InformationNotFoundException("Character is not found with id: " + selectedCharacterId)
        );
        Player createdPlayer = new Player(character);
        createdPlayer.setIdentityKey(generateIdentityKey(createdPlayer.getId()));
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

    public Player getPlayerByIdentity(String identityKey) {
        Player player = this.playerRepository.findByIdentityKey(identityKey).orElseThrow(() -> new InformationNotFoundException("Player is not found with identity key " + identityKey));
        if (player.getHealth() <= 0) throw new InformationInvalidException("Your character is dead");
        return player;
    }

    public Player increasePlayerDifficultyByOne(Long playerId) {
        Player player = this.playerRepository.findById(playerId).orElseThrow(() -> new InformationNotFoundException("Player is not found with id " + playerId));
        player.increaseDifficultyByOne();
        return playerRepository.save(player);
    }


    private String generateIdentityKey(Long playerId) {
        // Concatenate the player's ID with a random number
        String keyString = Long.toString(1L) + (int) (Math.random() * 10000000);

        // Convert the key to an integer and return it
        return keyString;
    }
}
