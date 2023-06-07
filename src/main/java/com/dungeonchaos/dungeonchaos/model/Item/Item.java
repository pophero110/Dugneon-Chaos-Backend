package com.dungeonchaos.dungeonchaos.model.Item;

import com.dungeonchaos.dungeonchaos.model.InventoryItem;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
public class Item {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "item")
    private Set<InventoryItem> inventoryItems = new HashSet<>();

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    public Item(Item item) {
        this.name = item.getName();
        this.rarity = item.getRarity();
        this.type = item.getType();
    }

    public Item() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public void setType(ItemType itemType) {
        this.type = itemType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemType getType() {
        return type;
    }
}


