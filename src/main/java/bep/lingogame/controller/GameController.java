package bep.lingogame.controller;

import bep.lingogame.domain.Game;
import bep.lingogame.domain.Player;
import bep.lingogame.service.GameService;
import bep.lingogame.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private GameService gameService;
    private PlayerService playerService;
    private PlayerController playerController;

    public GameController(GameService gameService,PlayerService playerService,PlayerController playerController) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.playerController = playerController;
    }

    @GetMapping
    public List<Game> findAll() {
        return gameService.findAll();
    }

    @PostMapping()
    public Game createNew() {
        Player player = playerService.findById(playerController.playerId);
        Game newGame = gameService.createNew(player);
        if (newGame != null){
            return newGame;
        }else{
            return null;
        }
    }

}
