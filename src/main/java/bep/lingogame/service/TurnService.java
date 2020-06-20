package bep.lingogame.service;

import bep.lingogame.domain.CharFeedback;
import bep.lingogame.domain.Game;
import bep.lingogame.domain.TotalFeedback;
import bep.lingogame.domain.Turn;
import bep.lingogame.repository.TurnRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnService {
    private TurnRepository turnRepository;
    private WordService wordService;
    public int mistakes = 0;
    public int score = 0;
    String guessedWord;
    String randomword2;
    CharFeedback charFeedback;
    TotalFeedback totalFeedback;


    public TurnService(TurnRepository turnRepository, WordService wordService) {
        this.turnRepository = turnRepository;
        this.wordService = wordService;
    }

    public List<Turn> findAll() {
        return turnRepository.findAll();
    }

    public Turn createNew(@RequestBody Game game ) {
        Turn turn = new Turn(null, randomword2,guessedWord,totalFeedback, mistakes,score, game, LocalDateTime.now());
        turnRepository.save(turn);
        return turn;
    }

    public String correctGuessedChars(Turn turn,String randomword,String correctGuessedChars,String numberOfLines) throws FileNotFoundException {
        randomword2=randomword;
        if (mistakes < 5) {
            if (turn.guessedWord.equals(randomword)) {//als het in een keer goed is
                mistakes = 0;
                correctGuessedChars = "";
                System.out.println("goed geraden");
                score+=100;
                guessedWord="";
                totalFeedback=totalFeedback.correct;
                randomword = wordService.returnRandomWord();
                String firstLetter = wordService.ReturnFirstChar(numberOfLines);
                return firstLetter;
            } else {//als het niet in een keer goed geraden is
                totalFeedback=totalFeedback.wrong;
                String guessedWord = turn.guessedWord;
                correctGuessedChars = checkGuessedChars(guessedWord, randomword);
                mistakes++;
                System.out.println("aantal foute gokbeurten " + mistakes);
            }
            System.out.println(correctGuessedChars + " je gok");
            return correctGuessedChars + " deze letters heb je goed";
        } else {
            score=0;
            return "game over ga naar: om een nieuwe game te starten";
        }
    }

    public String checkGuessedChars(String guessedWord, String randomwoord) {
        int counter = 0;
        String stringCorrecteChars = "";
        System.out.println(stringCorrecteChars);

        if (randomwoord.length() != guessedWord.length()) {
            System.out.println("Het gerade woord is niet de juiste lengte");
        } else {
            for (char letter : randomwoord.toCharArray()) {
                char currentChar = guessedWord.charAt(counter);//het karakter dat we nu checken
                if (letter == guessedWord.charAt(counter)) {//als de letters op de goede plek staan
                    charFeedback = CharFeedback.correct;
                    System.out.println(letter + " " + charFeedback);
                    stringCorrecteChars += letter;
                }
                else if (letter != guessedWord.charAt(counter)) {//als de letters niet op de goede plek staan
                    stringCorrecteChars = checkCharPresentOrAbsent(currentChar, stringCorrecteChars, randomwoord);
                }
                counter++;
            }
        }
        return stringCorrecteChars;
    }

    public String checkCharPresentOrAbsent(char currentChar, String stringCorrectChars, String randomwoord) {
        int currentCharOccuranceInRandomWord = checkCharOccurencesInRandomWord(currentChar, stringCorrectChars, randomwoord);
        if ((randomwoord.indexOf(currentChar)) >= 0) {//als de letter een positie groter dan 0 heeft oftewel erin zit
            //check of het letter al geweest is
            if ((stringCorrectChars.indexOf(currentChar)) >= 0) {//als de letter al gekozen is
                charFeedback = CharFeedback.present;
                System.out.println(currentChar + " " + charFeedback + " deze letter zit er zovaak in " + currentCharOccuranceInRandomWord);// letter is al geweest maar niet correct
            } else {//als de letter nog niet al gekozen is
                charFeedback = CharFeedback.present;
                System.out.println(currentChar + " " + charFeedback + " deze letter zit niet op de goede plek");
            }
        } else {
            charFeedback = CharFeedback.absent;
            System.out.println(currentChar + " " + charFeedback + " deze letter zit  er niet in");
        }
        stringCorrectChars += '_';
        return stringCorrectChars;
    }

    public int checkCharOccurencesInRandomWord(char currentChar, String stringCorrectChars, String randomwoord) {
        int index = randomwoord.indexOf(currentChar);
        int currentCharOccuranceInRandomWord = 0;
        if ((stringCorrectChars.indexOf(currentChar)) >= 0) {//als de letter al gekozen is
            while (index >= 0) {//check hoevaak de letter voorkomt
                index = randomwoord.indexOf(currentChar, index + 1);
                currentCharOccuranceInRandomWord += 1;
            }
        }
        return currentCharOccuranceInRandomWord;
    }

}