package com.dungeonchaos.dungeonchaos.repository;

import com.dungeonchaos.dungeonchaos.model.Fight.Fight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FightRepository extends JpaRepository<Fight, Long> {
}
