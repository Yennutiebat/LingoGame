package bep.lingogame.repository;

import bep.lingogame.domain.Game;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface GameRepository extends Repository<Game, Long> {
    List<Game> findAll();
    Game findById(Long id);
    void save(Game game);
}
