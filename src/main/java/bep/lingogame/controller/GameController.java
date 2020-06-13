package bep.lingogame.controller;

import bep.lingogame.domain.Game;
import bep.lingogame.service.GameService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> findAll() {
        return gameService.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Long createNew(@RequestBody Game game) {
        Game newGame = gameService.createNew(game);
        if (newGame != null){
            return newGame.id;
        }else{
            return null;
        }
    }

}
