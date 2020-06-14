package bep.lingogame.service;

import bep.lingogame.domain.Game;
import bep.lingogame.domain.Player;
import bep.lingogame.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(Long id) {
        return gameRepository.findById(id);
    }


    public Game createNew(Player player) {
        Game game = new Game(null,"in progress", player, LocalDateTime.now());
        gameRepository.save(game);
        return game;
    }
}
