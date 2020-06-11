package bep.lingogame.repository;

import bep.lingogame.domain.Player;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PlayerRepository extends Repository<Player, Long> {
    List<Player> findAll();
    void findById(Long id);

    void save(Player player);
}

