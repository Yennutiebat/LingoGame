package bep.lingogame.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Turn{
    @Id
    @GeneratedValue
    public Long id;
    public String guessedWord;
    public int mistakes;
    @ManyToOne
    Game game;
    @CreationTimestamp
    public LocalDateTime createdAt;

    public Turn(){
    }

    public Turn(Long id, String guessedWord, int mistakes, LocalDateTime createdAt){
        this.id=id;
        this.guessedWord = guessedWord;
        this.mistakes = mistakes;
        this.createdAt = createdAt;
    }
    public String getGuessedWord() {
        return guessedWord;
    }

    public int getMistakes() {
        return mistakes;
    }

    public Game getGame() {
        return game;
    }
    public void setGuessedWord(String guessedWord) {
        this.guessedWord = guessedWord;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}