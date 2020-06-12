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
    String aantalStreepjes;


    public TurnController(TurnService turnService, TextDeserializer textDeserializer) {
        this.turnService = turnService;
        this.textDeserializer = textDeserializer;
    }

    @GetMapping
    public String getRandomWord() throws FileNotFoundException {//geeft een random woord terug aan het get request met alleen de eerste letter zichtbaar
        aantalfout = 0;
        stringCorrecteletters = "";
        aantalStreepjes="";
        returnRandomWord();
        for(int i=0;i<randomwoord.length()-1;i++){
            aantalStreepjes+=" _ ";
        }
        String returnWaarde = randomwoord.substring(0,1)+aantalStreepjes+" "+randomwoord.length()+" tekens lang";
        System.out.println(returnWaarde);
        return returnWaarde;
    }

    public String returnRandomWord() throws FileNotFoundException {//geeft een random woord terug
        List<String> lingowords = textDeserializer.deserialize("src/main/resources/basiswoorden-aangepast.txt");
        List<String> checkedWords = loopWords(lingowords);
        int rnd = new Random().nextInt(checkedWords.size());
        randomwoord = checkedWords.get(rnd);
        System.out.println(randomwoord);
        return randomwoord;
    }

    public List<String> loopWords(List<String> content) {//looped door de woorden heen en stopt ze in een array
        List<String> checkedWords = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            String data = content.get(i);
            checkedWords.add(data);
        }
        return checkedWords;
    }
    public void storeCorrectwords(char letter){
            stringCorrecteletters += letter;
    }
    public void checkLetters(String guessedWord){

        int correctLetters = 0;
        int counter = 0;
        stringCorrecteletters="";
        try {
            for (char letter : randomwoord.toCharArray()) {
                if (letter == guessedWord.charAt(counter)) {//als de letters op de goede plek staan
                    System.out.println(letter + " valid");
                    storeCorrectwords(letter);
                    correctLetters++;
                } else if (letter != guessedWord.charAt(counter)) {//als de letters niet op de goede plek staan
                    checkIncorrectLetters(counter, guessedWord);
                }
                counter++;
            }
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("Het gerade woord is niet de juiste lengte");
        }
    }
    public void checkIncorrectLetters(int counter, String guessedWord){
        if ((randomwoord.indexOf(guessedWord.charAt(counter))) >= 0) {//als de letter een positie groter dan 0 heeft oftewel erin zit
            //check of het letter al geweest is
            if ((stringCorrecteletters.indexOf(guessedWord.charAt(counter))) >= 0) {//als de letter al gekozen is
                System.out.println(guessedWord.charAt(counter) + " incorrect deze letter heb je al gehad maar is niet correct");// letter is al geweest maar niet correct
            } else {//als de letter nog niet al gekozen is
                System.out.println(guessedWord.charAt(counter) + " present deze letter zit niet op de goede plek");
            }
        } else {
            System.out.println(guessedWord.charAt(counter) + " absent deze letter zit  er niet in");
        }
        storeCorrectwords('_');
    }

    @PostMapping(consumes = "application/json")
    public String guessWord(@RequestBody Turn turn) throws FileNotFoundException {
        if (aantalfout < 5) {
            if (turn.guessedWord.equals(randomwoord)) {//als het in een keer goed is
                aantalfout = 0;
                String stringCorrecteletters = "";
                return getRandomWord();
            } else {//als het niet in een keer goed geraden is
                String guessedWord = turn.guessedWord;
                checkLetters(guessedWord);
                aantalfout++;
                System.out.println("aantal foute gokbeurten " + aantalfout);
            }
            System.out.println(stringCorrecteletters+" je gok");
            return stringCorrecteletters+" deze letters heb je goed";
        } else {
            return "game over ga naar: om een nieuwe game te starten";
        }
    }
}
