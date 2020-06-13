package bep.lingogame.controller;


import bep.lingogame.domain.Feedback;
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
    int currentCharOccuranceInRandomWord = 0;
    int currentCharOccuranceInGuessedWord = 0;

    public TurnController(TurnService turnService, TextDeserializer textDeserializer) {
        this.turnService = turnService;
        this.textDeserializer = textDeserializer;
    }

    @GetMapping
    public String getRandomWord() throws FileNotFoundException {//geeft een random woord terug aan het get request met alleen de eerste letter zichtbaar
        aantalfout = 0;
        stringCorrecteletters = "";
        aantalStreepjes = "";
        returnRandomWord();
        for (int i = 0; i < randomwoord.length() - 1; i++) {
            aantalStreepjes += " _ ";
        }
        String returnWaarde = randomwoord.substring(0, 1) + aantalStreepjes + " " + randomwoord.length() + " tekens lang";
        System.out.println(returnWaarde);
        return returnWaarde;
    }

    public String returnRandomWord() throws FileNotFoundException {//geeft een random woord terug
        List<String> lingowords = textDeserializer.deserialize("src/main/resources/basiswoorden-aangepast.txt");
        List<String> checkedWords = loopTroughWords(lingowords);
        int rnd = new Random().nextInt(checkedWords.size());
        randomwoord = "baard"; //checkedWords.get(rnd);
        System.out.println(randomwoord);
        return "baard";
    }

    public List<String> loopTroughWords(List<String> content) {//looped door de woorden heen en stopt ze in een array
        List<String> checkedWords = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            String data = content.get(i);
            checkedWords.add(data);
        }
        return checkedWords;
    }

    public void storeCorrectwords(char letter) {
        stringCorrecteletters += letter;
    }

    public void checkGuessedLetters(String guessedWord) {
        int correctLetters = 0;
        int counter = 0;
        stringCorrecteletters = "";
        try {
            for (char letter : randomwoord.toCharArray()) {
                char currentChar = guessedWord.charAt(counter);//het karakter dat we nu checken
                if (letter == guessedWord.charAt(counter)) {//als de letters op de goede plek staan
                    System.out.println(letter +" "+ Feedback.correct);
                    //currentCharOccuranceInGuessedWord;
                    storeCorrectwords(letter);
                    correctLetters++;
                } else if (letter != guessedWord.charAt(counter)) {//als de letters niet op de goede plek staan
                    checkPresentOrAbsent(counter, guessedWord, currentChar);
                }
                counter++;
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Het gerade woord is niet de juiste lengte");
        }
    }

    public void checkPresentOrAbsent(int counter, String guessedWord, char currentChar) {
        checkOccurences(currentChar, guessedWord);
        if ((randomwoord.indexOf(currentChar)) >= 0) {//als de letter een positie groter dan 0 heeft oftewel erin zit
            //check of het letter al geweest is
            if ((stringCorrecteletters.indexOf(currentChar)) >= 0) {//als de letter al gekozen is
                System.out.println(currentChar +" "+ Feedback.present+" deze letter zit er zovaak in " + currentCharOccuranceInRandomWord);// letter is al geweest maar niet correct
            } else {//als de letter nog niet al gekozen is
                System.out.println(currentChar + " "+ Feedback.present+" deze letter zit niet op de goede plek");
            }
        } else {
            System.out.println(currentChar + " "+Feedback.absent+" deze letter zit  er niet in");
        }
        storeCorrectwords('_');
    }


    public void checkOccurences(char currentChar, String guessedWord) {
        int index = randomwoord.indexOf(currentChar);
        int index2 = randomwoord.indexOf(currentChar);
        int charactersLeft = currentCharOccuranceInRandomWord-currentCharOccuranceInGuessedWord;
        currentCharOccuranceInRandomWord = 0;
        currentCharOccuranceInGuessedWord = 0;
        if ((stringCorrecteletters.indexOf(currentChar)) >= 0) {//als de letter al gekozen is
            while (index >= 0) {//check hoevaak de letter voorkomt
                index = randomwoord.indexOf(currentChar, index + 1);
                currentCharOccuranceInRandomWord += 1;
            }
            while (index2 >= 0) {//check hoevaak de letter voorkomt in het geraden woord
                index2 = guessedWord.indexOf(currentChar, index2 + 1);
                currentCharOccuranceInGuessedWord += 1;
            }
        }
    }

    @PostMapping(consumes = "application/json")
    public String guessWord(@RequestBody Turn turn) throws FileNotFoundException {
        if (aantalfout < 5) {
            if (turn.guessedWord.equals(randomwoord)) {//als het in een keer goed is
                aantalfout = 0;
                String stringCorrecteletters = "";
                System.out.println("goed geraden");
                return getRandomWord();
            } else {//als het niet in een keer goed geraden is
                String guessedWord = turn.guessedWord;
                checkGuessedLetters(guessedWord);
                aantalfout++;
                System.out.println("aantal foute gokbeurten " + aantalfout);
            }
            System.out.println(stringCorrecteletters + " je gok");
            return stringCorrecteletters + " deze letters heb je goed";
        } else {
            return "game over ga naar: om een nieuwe game te starten";
        }
    }
}