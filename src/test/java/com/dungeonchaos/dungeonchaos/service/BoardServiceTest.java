package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.model.Board;
import com.dungeonchaos.dungeonchaos.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {
    @MockBean
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("it should generate 12 x 12 board")
    public void getBoardTest() {
        Optional<char[][]> boardMatrix = Optional.of(boardService.generateBoard());
        assertEquals(boardMatrix.isPresent(), true);
        assertEquals(boardMatrix.get().length, 12);
        for (char[] row : boardMatrix.get()) {
            assertEquals(row.length, 12);
        }
    }
}