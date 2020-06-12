package bep.lingogame.service;

import bep.lingogame.domain.Turn;
import bep.lingogame.repository.TurnRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnService {
    private TurnRepository turnRepository;

    public TurnService(TurnRepository turnRepository) {
        this.turnRepository = turnRepository;
    }

    public List<Turn> findAll() {
        return turnRepository.findAll();
    }

    public void findById(Long id) {
        turnRepository.findById(id);
    }

    public Turn createNew(Turn turnRestRequest) {
        Turn turn = new Turn(null, turnRestRequest.guessedWord, turnRestRequest.wordLength, LocalDateTime.now());

        turnRepository.save(turn);
        return turn;
    }
}