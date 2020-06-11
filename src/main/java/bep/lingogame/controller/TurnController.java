package bep.lingogame.controller;


import bep.lingogame.domain.Turn;
import bep.lingogame.service.TextDeserializer;
import bep.lingogame.service.TurnService;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/turn")
public class TurnController {
    private TurnService turnService;
    private TextDeserializer textDeserializer;
    private String randomwoord;
    private int aantalfout = 0;
    String stringCorrecteletters = "";


    public TurnController(TurnService turnService, TextDeserializer textDeserializer) {
        this.turnService = turnService;
        this.textDeserializer = textDeserializer;
    }

    @GetMapping
    public String findAll() throws FileNotFoundException {
        aantalfout = 0;
        stringCorrecteletters = "";
        return returnRandomWord();
    }

    public String returnRandomWord() throws FileNotFoundException {
        List<String> lingowords = textDeserializer.deserialize("src/main/resources/basiswoorden-aangepast.txt");
        List<String> checkedWords = loopWords(lingowords);
        int rnd = new Random().nextInt(checkedWords.size());
        randomwoord = checkedWords.get(rnd);
        System.out.println(randomwoord);
        return randomwoord;
    }

    public List<String> loopWords(List<String> content) {
        List<String> checkedWords = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            String data = content.get(i);
            checkedWords.add(data);
        }
        return checkedWords;
    }
    public void addToCorrectString(char letter){
        if(!(stringCorrecteletters.length()>=(randomwoord.length()))) {//als stringcorrecteletters nog niet is gevuld vul hem dan in
            stringCorrecteletters += letter;
        }
    }

    @PostMapping(consumes = "application/json")
    public String guessWord(@RequestBody Turn turn) throws FileNotFoundException {
        if (aantalfout < 5) {
            if (turn.guessedWord.equals(randomwoord)) {//als het in een keer goed is
                aantalfout = 0;
                String stringCorrecteletters = "";
                returnRandomWord();
            } else {//als het niet in een keer goed geraden is
                String guessedWord = turn.guessedWord;
                int correctLetters = 0;
                int counter = 0;
                for (char letter : randomwoord.toCharArray()) {
                    if (letter == guessedWord.charAt(counter)) {//als de letters op de goede plek staan
                        System.out.println(letter + " valid");
                        addToCorrectString(letter);
                        correctLetters++;
                    } else if (letter != guessedWord.charAt(counter)) {//als de letters niet op de goede plek staan
                        if ((randomwoord.indexOf(guessedWord.charAt(counter))) >= 0) {//als de letter een positie groter dan 0 heeft oftewel erin zit
                            //check of het letter al geweest is
                            addToCorrectString(letter);
                            if ((stringCorrecteletters.indexOf(guessedWord.charAt(counter))) >= 0) {//als de letter al gekozen is
                                System.out.println(guessedWord.charAt(counter) + " deze letter heb je al gehad maar is niet correct");// letter is al geweest maar niet correct
                            } else {//als de letter nog niet al gekozen is
                                System.out.println(guessedWord.charAt(counter) + " deze letter zit niet op de goede plek");
                            }
                            addToCorrectString('_');
                        } else {
                            System.out.println(guessedWord.charAt(counter) + " deze letter zit  er niet in");
                            addToCorrectString('_');
                        }
                    }
                    counter++;
                }
                aantalfout++;
                System.out.println("aantal foute gokbeurten " + aantalfout);
            }
            return stringCorrecteletters;
        } else {
            return "game over ga naar: om een nieuwe game te starten";
        }
    }
}
