package bep.lingogame.controller;


import bep.lingogame.domain.Turn;
import bep.lingogame.service.TurnService;
import bep.lingogame.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/turn")
public class TurnController {
    private TurnService turnService;
    private WordService wordService;
    private String randomword;
    private String correctGuessedChars;
    private String numberOfLines;
    private String guess;
    PlayerController playerController;


    public TurnController(TurnService turnService, WordService wordService,PlayerController playerController) {
        this.turnService = turnService;
        this.wordService = wordService;
        this.playerController = playerController;

    }

    @GetMapping
    public String getRandomWord() throws FileNotFoundException {//geeft een random woord terug aan het get request met alleen de eerste letter zichtbaar
        turnService.mistakes = 0;
        correctGuessedChars = "";
        numberOfLines = "";
        randomword = wordService.returnRandomWord();
        String firstLetter = wordService.ReturnFirstChar(numberOfLines);
        System.out.println(numberOfLines);
        return firstLetter;
    }

    @PostMapping(consumes = "application/json")
    public String guessWord(@RequestBody Turn turn) throws FileNotFoundException {
        guess =turnService.correctGuessedChars(turn,randomword,correctGuessedChars,numberOfLines); //String guess =turnService.correctGuessedChars(turn,randomword,correctGuessedChars,numberOfLines);
        turnService.createNew(playerController.newGame);
        return guess;
    }
}