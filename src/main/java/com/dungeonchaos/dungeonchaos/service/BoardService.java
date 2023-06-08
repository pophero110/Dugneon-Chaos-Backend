package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.model.Board;
import com.dungeonchaos.dungeonchaos.repository.BoardRepository;
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

    private static final char MONSTER_SYMBOL = 'M';
    private static final char TREASURE_SYMBOL = 'T';
    private static final double MONSTER_PROBABILITY = 0.2;
    private static final char[] VALID_LOCATIONS = { 'P' };
    private static final int NUMBER_OF_TREASURE_PER_LEVEL = 1;


    @Autowired
    BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Optional<char[][]> getBoard() {
        ObjectMapper objectMapper = new ObjectMapper();
        Board board = boardRepository.findAll().get(0);
        char[][] boardMatrix;
        try {
            boardMatrix = objectMapper.readValue(board.getBoardJson(), char[][].class);
            this.generateTreasure(boardMatrix);
            this.generateMonsters(boardMatrix);
        } catch (JsonMappingException e) {
            return Optional.empty();
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
        return Optional.of(boardMatrix);
    }

    private void generateMonsters(char[][] boardMatrix) {
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

    private void generateTreasure(char[][] boardMatrix) {
        Random random = new Random();
        int count = 0;
        while (count < NUMBER_OF_TREASURE_PER_LEVEL) {
            int row = random.nextInt(boardMatrix.length);
            int col = random.nextInt(boardMatrix[row].length);
            char cell = boardMatrix[row][col];
            if (isValidLocation(cell)) {
                boardMatrix[row][col] = TREASURE_SYMBOL;
                count++;
            }
        }
    }

    private static boolean isValidLocation(char cell) {
        for (char validLocation : VALID_LOCATIONS) {
            if (cell == validLocation) {
                return true;
            }
        }
        return false;
    }
}
