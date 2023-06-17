package com.dungeonchaos.dungeonchaos.controller;


import com.dungeonchaos.dungeonchaos.model.Inventory;
import com.dungeonchaos.dungeonchaos.request.InventoryRequest;
import com.dungeonchaos.dungeonchaos.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/inventories")
public class InventoryController {


    private final InventoryService inventoryService;

    @Autowired
    InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long inventoryId) {
        Inventory inventory = inventoryService.getInventoryById(inventoryId);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PostMapping("/{inventoryId}/addItem")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Inventory> addItemToInventory(@PathVariable Long inventoryId, @RequestBody InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryService.addItemToInventory(inventoryId, inventoryRequest.getItemId());
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PutMapping("/{inventoryId}/removeItem")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Inventory> removeItemFromInventory(@PathVariable Long inventoryId, @RequestBody InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryService.removeItemFromInventory(inventoryId, inventoryRequest.getItemId());
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }
}
