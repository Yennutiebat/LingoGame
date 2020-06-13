package bep.lingogame.service;

import bep.lingogame.domain.Feedback;
import bep.lingogame.domain.Turn;
import bep.lingogame.repository.TurnRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TurnService {
    private TurnRepository turnRepository;
    private TextDeserializer textDeserializer;

    public TurnService(TurnRepository turnRepository, TextDeserializer textDeserializer) {
        this.turnRepository = turnRepository;
        this.textDeserializer = textDeserializer;
    }

    public List<Turn> findAll() {
        return turnRepository.findAll();
    }

    public void findById(Long id) {
        turnRepository.findById(id);
    }

    public Turn createNew(Turn turnRestRequest) {
        Turn turn = new Turn(null, turnRestRequest.guessedWord, turnRestRequest.aantalFout, LocalDateTime.now());

        turnRepository.save(turn);
        return turn;
    }

    public List<String> loopTroughWords(List<String> content) {//looped door de woorden heen en stopt ze in een array
        List<String> checkedWords = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            String data = content.get(i);
            checkedWords.add(data);
        }
        return checkedWords;
    }

    public String returnRandomWord() throws FileNotFoundException {//geeft een random woord terug
        List<String> lingowords = textDeserializer.deserialize("src/main/resources/basiswoorden-aangepast.txt");
        List<String> checkedWords = loopTroughWords(lingowords);
        int rnd = new Random().nextInt(checkedWords.size());
        String randomwoord = "baard"; //checkedWords.get(rnd);
        System.out.println(randomwoord);
        return "baard";
    }


    public String checkGuessedChars(String guessedWord, String stringCorrecteChars, String randomwoord, int currentCharOccuranceInRandomWord) {
        int correctChars = 0;
        int counter = 0;
        stringCorrecteChars = "";
        try {
            for (char letter : randomwoord.toCharArray()) {
                char currentChar = guessedWord.charAt(counter);//het karakter dat we nu checken
                if (letter == guessedWord.charAt(counter)) {//als de letters op de goede plek staan
                    System.out.println(letter + " " + Feedback.correct);
                    stringCorrecteChars += letter;
                    correctChars++;
                } else if (letter != guessedWord.charAt(counter)) {//als de letters niet op de goede plek staan
                    stringCorrecteChars= checkCharPresentOrAbsent(counter, guessedWord, currentChar, stringCorrecteChars, randomwoord, currentCharOccuranceInRandomWord);
                }
                counter++;
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Het gerade woord is niet de juiste lengte");
        }
        return stringCorrecteChars;
    }

    public String checkCharPresentOrAbsent(int counter, String guessedWord, char currentChar, String stringCorrectChars, String randomwoord, int currentCharOccuranceInRandomWord) {
        currentCharOccuranceInRandomWord= checkCharOccurencesInRandomWord(currentChar, guessedWord, stringCorrectChars, randomwoord, currentCharOccuranceInRandomWord);
        if ((randomwoord.indexOf(currentChar)) >= 0) {//als de letter een positie groter dan 0 heeft oftewel erin zit
            //check of het letter al geweest is
            if ((stringCorrectChars.indexOf(currentChar)) >= 0) {//als de letter al gekozen is
                System.out.println(currentChar + " " + Feedback.present + " deze letter zit er zovaak in " + currentCharOccuranceInRandomWord);// letter is al geweest maar niet correct
            } else {//als de letter nog niet al gekozen is
                System.out.println(currentChar + " " + Feedback.present + " deze letter zit niet op de goede plek");
            }
        } else {
            System.out.println(currentChar + " " + Feedback.absent + " deze letter zit  er niet in");
        }
        stringCorrectChars += '_';
        return stringCorrectChars;
    }

    public int checkCharOccurencesInRandomWord(char currentChar, String guessedWord, String stringCorrectChars, String randomwoord, int currentCharOccuranceInRandomWord) {
        int index = randomwoord.indexOf(currentChar);
        currentCharOccuranceInRandomWord = 0;
        if ((stringCorrectChars.indexOf(currentChar)) >= 0) {//als de letter al gekozen is
            while (index >= 0) {//check hoevaak de letter voorkomt
                index = randomwoord.indexOf(currentChar, index + 1);
                currentCharOccuranceInRandomWord += 1;
            }
        }
        return currentCharOccuranceInRandomWord;
    }

}