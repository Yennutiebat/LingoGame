/*package bep.lingogame.controller;


import bep.lingogame.domain.Turn;
import bep.lingogame.service.TurnService;
import bep.lingogame.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
*//*
@RestController
@RequestMapping("/api/turn")
public class TurnController {
    private TurnService turnService;
    private WordService wordService;
    private String randomword;
    private int wrongGuesses = 0;
    private String correctGuessedChars;
    private String numberOfLines;
    int currentCharOccuranceInRandomWord = 0;

    public TurnController(TurnService turnService, WordService wordService) {
        this.turnService = turnService;
        this.wordService = wordService;
    }

    @GetMapping
    public String getRandomWord() throws FileNotFoundException {//geeft een random woord terug aan het get request met alleen de eerste letter zichtbaar
        wrongGuesses = 0;
        correctGuessedChars = "";
        numberOfLines = "";
        randomword = wordService.returnRandomWord();
        String firstLetter = wordService.ReturnFirstChar(numberOfLines);
        System.out.println(numberOfLines);
        return firstLetter;
    }

    @PostMapping(consumes = "application/json")
    public String guessWord(@RequestBody Turn turn) throws FileNotFoundException {
        if (wrongGuesses < 5) {
            if (turn.guessedWord.equals(randomword)) {//als het in een keer goed is
                wrongGuesses = 0;
                correctGuessedChars = "";
                System.out.println("goed geraden");
                randomword = wordService.returnRandomWord();
                String firstLetter = wordService.ReturnFirstChar(numberOfLines);
                return firstLetter;
            } else {//als het niet in een keer goed geraden is
                String guessedWord = turn.guessedWord;
                correctGuessedChars =turnService.checkGuessedChars(guessedWord, randomword);
                wrongGuesses++;
                System.out.println("aantal foute gokbeurten " + wrongGuesses);
            }
            System.out.println(correctGuessedChars + " je gok");
            return correctGuessedChars + " deze letters heb je goed";
        } else {
            return "game over ga naar: om een nieuwe game te starten";
        }
    }
}*/