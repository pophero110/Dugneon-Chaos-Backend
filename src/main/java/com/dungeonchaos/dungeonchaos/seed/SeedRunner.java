package com.dungeonchaos.dungeonchaos.seed;

import com.dungeonchaos.dungeonchaos.model.Board;
import com.dungeonchaos.dungeonchaos.model.Character;
import com.dungeonchaos.dungeonchaos.repository.BoardRepository;
import com.dungeonchaos.dungeonchaos.repository.CharacterRepository;
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


    @Autowired
    SeedRunner(CharacterRepository characterRepository, BoardRepository boardRepository) {
        this.characterRepository = characterRepository;
        this.boardRepository = boardRepository;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCharacters();
        createBoard();
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
            // Handle the exception
            e.printStackTrace();
            return;
        }

    }
    private void createCharacters() {
        Character warrior = new Character("Warrior", 90, 10, 20, 30);
        Character rogue = new Character("Rogue", 80, 15, 10, 40);

        characterRepository.save(warrior);
        characterRepository.save(rogue);
    }
}
