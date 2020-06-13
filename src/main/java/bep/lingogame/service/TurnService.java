package bep.lingogame.service;

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

    public TurnService(TurnRepository turnRepository,TextDeserializer textDeserializer) {
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



}