package com.dungeonchaos.dungeonchaos.repository;

import com.dungeonchaos.dungeonchaos.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
