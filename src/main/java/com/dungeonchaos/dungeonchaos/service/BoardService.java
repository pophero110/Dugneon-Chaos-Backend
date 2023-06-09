package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.exception.InformationNotFoundException;
import com.dungeonchaos.dungeonchaos.model.Board;
import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.repository.BoardRepository;
import com.dungeonchaos.dungeonchaos.repository.PlayerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    private PlayerRepository playerRepository;

    private static final char MONSTER_SYMBOL = 'M';
    private static final char TREASURE_SYMBOL = 'T';
    private static final double MONSTER_PROBABILITY = 0.15;
    private static final char[] VALID_LOCATIONS = {'P'};
    private static final int NUMBER_OF_TREASURE_PER_LEVEL = 1;


    @Autowired
    BoardService(BoardRepository boardRepository, PlayerRepository playerRepository) {
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
    }

    /**
     * Generates a board matrix for a given player.
     * @param playerId The ID of the player.
     * @return The generated board matrix.
     * @throws InformationNotFoundException if the player is not found with the given ID.
     */
    public char[][] generateBoard(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(()-> new InformationNotFoundException("Player is not found with id " + playerId));
        MazeGenerator mazeGenerator = new MazeGenerator(12, 12);
        char[][] boardMatrix = mazeGenerator.generateMaze();
        this.generateTreasure(boardMatrix, player.getDifficulty());
        this.generateMonsters(boardMatrix, player.getDifficulty());
        return boardMatrix;
    }

    /**
     * Generates treasure on the board matrix.
     * @param boardMatrix The board matrix.
     * @param playerDifficulty The difficulty level of the player.
     */
    private void generateMonsters(char[][] boardMatrix, int playerDifficulty) {
        Random random = new Random();
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                char cell = boardMatrix[i][j];
                if (isValidLocation(cell)) {
                    if (random.nextDouble() < MONSTER_PROBABILITY) {
                        boardMatrix[i][j] = MONSTER_SYMBOL;
                    }
                }
            }
        }
    }


    /**
     * Generates treasure on the board matrix.
     * @param boardMatrix The board matrix.
     * @param playerDifficulty The difficulty level of the player.
     */
    private void generateTreasure(char[][] boardMatrix, int playerDifficulty) {
        Random random = new Random();
        int count = 0;
        while (count < NUMBER_OF_TREASURE_PER_LEVEL + playerDifficulty) {
            int row = random.nextInt(boardMatrix.length);
            int col = random.nextInt(boardMatrix[row].length);
            char cell = boardMatrix[row][col];
            if (isValidLocation(cell)) {
                boardMatrix[row][col] = TREASURE_SYMBOL;
                count++;
            }
        }
    }

    /**
     * Checks if a given cell is a valid location.
     * @param cell The cell to check.
     * @return True if the cell is a valid location, false otherwise.
     */
    private static boolean isValidLocation(char cell) {
        for (char validLocation : VALID_LOCATIONS) {
            if (cell == validLocation) {
                return true;
            }
        }
        return false;
    }
}
