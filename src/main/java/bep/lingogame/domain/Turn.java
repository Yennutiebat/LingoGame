package bep.lingogame.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Turn{
    @Id
    @GeneratedValue
    public Long id;
    public String guessedWord;
    public int aantalFout;
    @CreationTimestamp
    public LocalDateTime createdAt;

    public Turn(){
    }

    public Turn(Long id, String guessedWord, int aantalFout, LocalDateTime createdAt){
        this.id=id;
        this.guessedWord = guessedWord;
        this.aantalFout = aantalFout;
        this.createdAt = createdAt;
    }
}