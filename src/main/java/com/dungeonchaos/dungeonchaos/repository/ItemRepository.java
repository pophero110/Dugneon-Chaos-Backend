package com.dungeonchaos.dungeonchaos.repository;


import com.dungeonchaos.dungeonchaos.model.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
