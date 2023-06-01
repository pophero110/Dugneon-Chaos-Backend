package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Autowired
    BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public char[][] generateBoard() {
        char[][] board = {
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
        return board;
    }
}
