package bep.lingogame.service;

import bep.lingogame.domain.Player;
import bep.lingogame.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public void findById(Long id) {
        playerRepository.findById(id);
    }

    public Player createNew(Player playerRestRequest) {
        Player player = new Player(null, playerRestRequest.name, playerRestRequest.score, LocalDateTime.now());

        playerRepository.save(player);
        return player;
    }
}