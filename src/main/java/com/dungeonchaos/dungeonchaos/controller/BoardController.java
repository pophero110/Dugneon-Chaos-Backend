package com.dungeonchaos.dungeonchaos.controller;


import com.dungeonchaos.dungeonchaos.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/boards")
public class BoardController {

    private BoardService boardService;

    @Autowired
    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping(path = "")
    public char[][] createBoard() {
        return boardService.generateBoard();
    }
}
