package com.dungeonchaos.dungeonchaos.controller;

import com.dungeonchaos.dungeonchaos.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/boards")
public class BoardController {

    private BoardService boardService;

    @Autowired
    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    static HashMap<String, Object> responseBody = new HashMap<>();

    @GetMapping(path = "")
    public ResponseEntity<?> getBoard() {
        char[][] board = boardService.generateBoard();
        responseBody.put("board", board);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
