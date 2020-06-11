package bep.lingogame.controller;


import bep.lingogame.domain.Turn;
import bep.lingogame.service.TextDeserializer;
import bep.lingogame.service.TurnService;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/turn")
public class TurnController {
    private TurnService turnService;
    private TextDeserializer textDeserializer;
    private String randomwoord;

    public TurnController(TurnService turnService, TextDeserializer textDeserializer) {
        this.turnService = turnService;
        this.textDeserializer = textDeserializer;
    }

    @GetMapping
    public List<Turn> findAll() {
        return turnService.findAll();
    }

    public String returRandomWord() throws FileNotFoundException {
        List<String> lingowords = textDeserializer.deserialize("src/main/resources/basiswoorden-aangepast.txt");

        List<String> checkedWords = loopWords(lingowords);
        randomwoord = checkedWords.get(0);
        System.out.println(checkedWords.get(0)+"1");
        System.out.println(randomwoord+"2");
        return checkedWords.get(0);
    }

    public List<String> loopWords(List<String> content) {
        List<String> checkedWords = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            String data = content.get(i);
            checkedWords.add(data);
            System.out.println(checkedWords.get(0)+"gechecked");

        }
        System.out.println(checkedWords.get(0)+"gechecked");

        return checkedWords;


    }

    @PostMapping(consumes = "application/json")
    public String guessWord(@RequestBody Turn turn) throws FileNotFoundException {
        if (turn.guessedWord.equals(randomwoord)) {
            returRandomWord();
            return "goed geraden a niffauw";
        } else {
            returRandomWord();
            return "fout a niffauw " + turn.guessedWord;
        }
    }/*
    @PostMapping(consumes = "application/json")
    public Long createNew(@RequestBody Turn turn) {
        Turn newTurn = turnService.createNew(turn);
        if (newTurn != null) {
            return newTurn.id;
        } else {
            return null;
        }
    }*/
}