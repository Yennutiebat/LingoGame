package bep.lingogame.controller;


import bep.lingogame.domain.Turn;
import bep.lingogame.service.TextDeserializer;
import bep.lingogame.service.TurnService;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/turn")
public class TurnController {
    private TurnService turnService;
    private TextDeserializer textDeserializer;
    private String randomwoord;
    private int aantalfout = 0;
    private String stringCorrectChars;
    private String aantalStreepjes;
    int currentCharOccuranceInRandomWord = 0;

    public TurnController(TurnService turnService, TextDeserializer textDeserializer) {
        this.turnService = turnService;
        this.textDeserializer = textDeserializer;
    }

    @GetMapping
    public String getRandomWord() throws FileNotFoundException {//geeft een random woord terug aan het get request met alleen de eerste letter zichtbaar
        aantalfout = 0;
        stringCorrectChars = "";
        aantalStreepjes = "";
        randomwoord=turnService.returnRandomWord();
        for (int i = 0; i < randomwoord.length() - 1; i++) {
            aantalStreepjes += " _ ";
        }
        String returnWaarde = randomwoord.substring(0, 1) + aantalStreepjes + " " + randomwoord.length() + " tekens lang";
        System.out.println(returnWaarde);
        return returnWaarde;
    }


    @PostMapping(consumes = "application/json")
    public String guessWord(@RequestBody Turn turn) throws FileNotFoundException {
        if (aantalfout < 5) {
            if (turn.guessedWord.equals(randomwoord)) {//als het in een keer goed is
                aantalfout = 0;
                String stringCorrectChars = "";
                System.out.println("goed geraden");
                return getRandomWord();
            } else {//als het niet in een keer goed geraden is
                String guessedWord = turn.guessedWord;
                stringCorrectChars=turnService.checkGuessedChars(guessedWord,stringCorrectChars,randomwoord,currentCharOccuranceInRandomWord);
                aantalfout++;
                System.out.println("aantal foute gokbeurten " + aantalfout);
            }
            System.out.println(stringCorrectChars + " je gok");
            return stringCorrectChars + " deze letters heb je goed";
        } else {
            return "game over ga naar: om een nieuwe game te starten";
        }
    }
}