package com.dungeonchaos.dungeonchaos.controller;


import com.dungeonchaos.dungeonchaos.model.Player;
import com.dungeonchaos.dungeonchaos.service.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/inventoryItems")
public class InventoryItemController {


    private InventoryItemService inventoryItemService;

    @Autowired
    InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @PutMapping("/{inventoryItemId}/equipWeapon")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> equipWeapon(@PathVariable Long inventoryItemId) {
        Player player = inventoryItemService.equipWeapon(inventoryItemId);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PutMapping("/{inventoryItemId}/equipArmor")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> equipArmor(@PathVariable Long inventoryItemId) {
        Player player = inventoryItemService.equipArmor(inventoryItemId);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PutMapping("/{inventoryItemId}/unequipWeapon")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> unequipWeapon(@PathVariable Long inventoryItemId) {
        Player player = inventoryItemService.unequipWeapon(inventoryItemId);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PutMapping("/{inventoryItemId}/unequipArmor")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Player> unequipArmor(@PathVariable Long inventoryItemId) {
        Player player = inventoryItemService.unequipArmor(inventoryItemId);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
}
