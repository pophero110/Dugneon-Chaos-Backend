package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {
    @MockBean
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("it should generate 12 x 12 board")
    public void generateBoardTest() {
        char[][] board = boardService.generateBoard();
        assertEquals(board.length, 12);
        for (char[] row : board) {
            assertEquals(row.length, 12);
        }
    }
}