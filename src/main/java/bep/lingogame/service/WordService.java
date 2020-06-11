package bep.lingogame.service;

import bep.lingogame.domain.Word;
import bep.lingogame.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WordService {
    private WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    public void findById(Long id) {
        wordRepository.findById(id);
    }

    public Word createNew(Word wordRestRequest) {
        Word word = new Word(null, wordRestRequest.guessedWord, wordRestRequest.wordLength, LocalDateTime.now());

        wordRepository.save(word);
        return word;
    }
}
