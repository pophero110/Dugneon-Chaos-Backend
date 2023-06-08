package com.dungeonchaos.dungeonchaos.service;

import com.dungeonchaos.dungeonchaos.exception.InformationNotFoundException;
import com.dungeonchaos.dungeonchaos.model.Item.Item;
import com.dungeonchaos.dungeonchaos.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public List<Item> getItems() {
         List<Item> items = itemRepository.findAll();
        return items;
    }
}
