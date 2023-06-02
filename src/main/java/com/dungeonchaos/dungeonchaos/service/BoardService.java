package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.model.Board;
import com.dungeonchaos.dungeonchaos.repository.BoardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {
    private BoardRepository boardRepository;

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
        } catch (JsonMappingException e) {
            return Optional.empty();
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
        return Optional.of(boardMatrix);
    }
}
