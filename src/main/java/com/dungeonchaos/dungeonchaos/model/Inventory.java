package com.dungeonchaos.dungeonchaos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<InventoryItem> inventoryItems = new HashSet<>();

    public Inventory() {
    }

    public Long getId() {
        return id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }


    public void setInventoryItems(Set<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public void removeInventoryItem(InventoryItem inventoryItem) {
        inventoryItems.remove(inventoryItem);
        inventoryItem.setInventory(null);
    }

    @JsonIgnore
    public Player getPlayer() {
        return player;
    }
}

