package com.dungeonchaos.dungeonchaos.model;

import com.dungeonchaos.dungeonchaos.model.Item.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "inventory_item")
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private boolean isEquipped;

    public InventoryItem(Inventory inventory, Item item) {
        this.inventory = inventory;
        this.item = item;
        this.itemQuantity = 1;
    }
    public InventoryItem(){}
    private int itemQuantity;

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void increaseItemQuantityByOne() {
        this.itemQuantity++;
    }

    public void decreaseItemQuantityByOne() {
        this.itemQuantity--;
    }

    @JsonIgnore
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        this.itemQuantity = 1;
    }

    public Long getId() {
        return id;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }
}