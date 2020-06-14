package bep.lingogame.controller;

import bep.lingogame.domain.Game;
import bep.lingogame.domain.Player;
import bep.lingogame.service.GameService;
import bep.lingogame.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private PlayerService playerService;
    private GameService gameService;
    public Game newGame;
    public Long playerId;
    public Long gameId;

    public PlayerController(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @GetMapping
    public List<Player> findAll() {
        return playerService.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Long createNew(@RequestBody Player player) {
        Player newPlayer = playerService.createNew(player);
         newGame = gameService.createNew(newPlayer);
        if (newPlayer != null){
            gameId = newGame.id;
            return newPlayer.id;
        }else{
            return null;
        }
    }
}