package bep.lingogame.controller;


import bep.lingogame.domain.Player;
import bep.lingogame.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> findAll() {
        return playerService.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Long createNew(@RequestBody Player player) {
        Player newplayer = playerService.createNew(player);
        if (newplayer != null){
            return newplayer.id;
        }else{
            return null;
        }
    }

}