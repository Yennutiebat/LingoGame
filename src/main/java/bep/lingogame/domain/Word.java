package bep.lingogame.domain;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Word{
    @Id
    @GeneratedValue
    public Long id;
    public String guessedWord;
    @ManyToOne
    Turn turn;
    @CreationTimestamp
    public LocalDateTime createdAt;

    public Word(){
    }

    public Word(Long id, String guessedWord, LocalDateTime createdAt){
        this.id=id;
        this.guessedWord = guessedWord;
        this.createdAt = createdAt;
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public void setGuessedWord(String guessedWord) {
        this.guessedWord = guessedWord;
    }
}