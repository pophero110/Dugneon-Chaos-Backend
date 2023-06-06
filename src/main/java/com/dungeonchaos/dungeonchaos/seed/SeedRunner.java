package com.dungeonchaos.dungeonchaos.seed;

import com.dungeonchaos.dungeonchaos.model.Board;
import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.model.Monster;
import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.repository.BoardRepository;
import com.dungeonchaos.dungeonchaos.repository.CharacterRepository;
import com.dungeonchaos.dungeonchaos.repository.MonsterRepository;
import com.dungeonchaos.dungeonchaos.repository.PlayerRepository;
import com.dungeonchaos.dungeonchaos.service.FightService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedRunner implements ApplicationRunner {
    private final CharacterRepository characterRepository;

    private final BoardRepository boardRepository;

    private final MonsterRepository monsterRepository;
    private final PlayerRepository playerRepository;

    private final FightService fightService;


    @Autowired
    SeedRunner(CharacterRepository characterRepository, BoardRepository boardRepository, MonsterRepository monsterRepository, PlayerRepository playerRepository, FightService fightService) {
        this.characterRepository = characterRepository;
        this.boardRepository = boardRepository;
        this.monsterRepository = monsterRepository;
        this.playerRepository = playerRepository;
        this.fightService = fightService;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCharactersAndPlayer();
        createBoard();
        createMonsters();
        createFight();
    }

    private void createBoard() {
        ObjectMapper objectMapper = new ObjectMapper();
        char[][] boardMatrix = {
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'W', 'P', 'W'},
                {'W', 'W', 'P', 'W', 'W', 'W', 'W', 'W', 'P', 'W', 'P', 'W'},
                {'P', 'P', 'P', 'W', 'P', 'P', 'S', 'W', 'P', 'W', 'P', 'P'},
                {'P', 'W', 'P', 'W', 'P', 'W', 'W', 'W', 'W', 'W', 'W', 'W'},
                {'P', 'W', 'P', 'W', 'P', 'W', 'P', 'P', 'P', 'P', 'P', 'W'},
                {'P', 'W', 'P', 'W', 'P', 'W', 'P', 'W', 'W', 'W', 'P', 'W'},
                {'P', 'W', 'P', 'W', 'P', 'P', 'P', 'W', 'P', 'W', 'P', 'W'},
                {'P', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'P', 'W', 'P', 'W'},
                {'P', 'W', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'W'},
                {'P', 'W', 'P', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'E', 'W'}
        };

        try {
            String jsonData = objectMapper.writeValueAsString(boardMatrix);
            Board board = new Board(jsonData);
            boardRepository.save(board);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    private void createCharactersAndPlayer() {
        Character warrior = new Character("Warrior", 90, 10, 20, 30);
        Character rogue = new Character("Rogue", 80, 30, 10, 40);
        Character slow = new Character("Slow Speed", 80, 15, 10, 0);
        Character fast = new Character("Fast Speed", 80, 15, 10, 999);
        Player playerWarrior = new Player(warrior);
        Player playerRogue = new Player(rogue);
        Player playerSlow = new Player(slow);
        Player playerFast = new Player(fast);
        playerRepository.save(playerWarrior);
        playerRepository.save(playerRogue);
        playerRepository.save(playerSlow);
        playerRepository.save(playerFast);
        characterRepository.save(warrior);
        characterRepository.save(rogue);
        characterRepository.save(slow);
        characterRepository.save(fast);
    }

    private void createMonsters() {
        Monster goblin = new Monster("Goblin", 30, 5,0,20);

        monsterRepository.save(goblin);
    }

    private void createFight() {
        fightService.startFight(1L, 1L);
    }
}
