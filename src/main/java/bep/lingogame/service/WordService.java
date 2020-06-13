package bep.lingogame.service;

import bep.lingogame.domain.Word;
import bep.lingogame.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WordService {
    private WordRepository wordRepository;
    private TextDeserializer textDeserializer;
    private String randomWord;

    public WordService(WordRepository wordRepository, TextDeserializer textDeserializer) {
        this.wordRepository = wordRepository;
        this.textDeserializer = textDeserializer;

    }

    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    public void findById(Long id) {
        wordRepository.findById(id);
    }

    public Word createNew(Word wordRestRequest) {
        Word word = new Word(null, wordRestRequest.guessedWord, LocalDateTime.now());
        wordRepository.save(word);
        return word;
    }

    public String ReturnFirstLetter(String numberOfLines) {
        for (int i = 0; i < randomWord.length() - 1; i++) {
            numberOfLines += " _ ";
        }
        String returnWaarde = randomWord.substring(0, 1) + numberOfLines + " " + randomWord.length() + " tekens lang";
        System.out.println(returnWaarde+" randomWord "+randomWord);
        return returnWaarde;
    }

    public String returnRandomWord() throws FileNotFoundException {//geeft een random woord terug
        List<String> lingowords = textDeserializer.deserialize("src/main/resources/basiswoorden-aangepast.txt");
        List<String> checkedWords = loopTroughWords(lingowords);
        int rnd = new Random().nextInt(checkedWords.size());
        randomWord = checkedWords.get(rnd);
        System.out.println(randomWord+" random ");
        return randomWord;
    }

    public List<String> loopTroughWords(List<String> content) {//looped door de woorden heen en stopt ze in een array
        List<String> checkedWords = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            String data = content.get(i);
            checkedWords.add(data);
        }
        return checkedWords;
    }


}
