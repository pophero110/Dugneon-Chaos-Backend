package com.dungeonchaos.dungeonchaos.controller;


import com.dungeonchaos.dungeonchaos.model.Item.Item;
import com.dungeonchaos.dungeonchaos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/items")
public class ItemController {


    private final ItemService itemService;

    @Autowired
    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("")
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items= itemService.getItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
