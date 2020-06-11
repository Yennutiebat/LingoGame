package bep.lingogame.domain;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Word{
    @Id
    @GeneratedValue
    public Long id;
    public String guessedWord;
    public int wordLength;
    @CreationTimestamp
    public LocalDateTime createdAt;

    public Word(){
    }

    public Word(Long id, String guessedWord, int wordLength, LocalDateTime createdAt){
        this.id=id;
        this.guessedWord = guessedWord;
        this.createdAt = createdAt;
    }
}