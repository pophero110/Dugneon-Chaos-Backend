package com.dungeonchaos.dungeonchaos.repository;

import com.dungeonchaos.dungeonchaos.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByIdentityKey(String identityKey);
}
