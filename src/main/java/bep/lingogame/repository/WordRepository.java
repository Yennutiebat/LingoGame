package bep.lingogame.repository;

import bep.lingogame.domain.Word;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface WordRepository extends Repository<Word, Long> {
    List<Word> findAll();
    Word findById(Long id);

    void save(Word Word);
}

