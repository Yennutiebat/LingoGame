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
    private int wrongGuesses = 0;
    private String stringCorrectChars;
    private String numberOfLines;
    int currentCharOccuranceInRandomWord = 0;

    public TurnController(TurnService turnService, WordService wordService) {
        this.turnService = turnService;
        this.wordService = wordService;
    }

    @GetMapping
    public String getRandomWord() throws FileNotFoundException {//geeft een random woord terug aan het get request met alleen de eerste letter zichtbaar
        wrongGuesses = 0;
        stringCorrectChars = "";
        numberOfLines = "";
        randomword = wordService.returnRandomWord();
        String firstLetter = wordService.ReturnFirstLetter(numberOfLines);
        System.out.println(numberOfLines);
        return firstLetter;
    }

    @PostMapping(consumes = "application/json")
    public String guessWord(@RequestBody Turn turn) throws FileNotFoundException {
        if (wrongGuesses < 5) {
            if (turn.guessedWord.equals(randomword)) {//als het in een keer goed is
                wrongGuesses = 0;
                String stringCorrectChars = "";
                System.out.println("goed geraden");
                return getRandomWord();
            } else {//als het niet in een keer goed geraden is
                String guessedWord = turn.guessedWord;
                stringCorrectChars=turnService.checkGuessedChars(guessedWord,stringCorrectChars, randomword,currentCharOccuranceInRandomWord);
                wrongGuesses++;
                System.out.println("aantal foute gokbeurten " + wrongGuesses);
            }
            System.out.println(stringCorrectChars + " je gok");
            return stringCorrectChars + " deze letters heb je goed";
        } else {
            return "game over ga naar: om een nieuwe game te starten";
        }
    }
}