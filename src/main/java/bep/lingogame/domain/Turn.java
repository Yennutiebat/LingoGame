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
    public String randomWord;
    public String guessedWord;
    public TotalFeedback totalFeedback;
    public int mistakes;
    public int score;
    @ManyToOne
    public Game game;
    @CreationTimestamp
    public LocalDateTime createdAt;

    public Turn(){
    }

    public Turn(Long id,String randomWord, String guessedWord,TotalFeedback totalFeedback, int mistakes,int score, Game game, LocalDateTime createdAt){
        this.id=id;
        this.randomWord = randomWord;
        this.guessedWord = guessedWord;
        this.totalFeedback = totalFeedback;
        this.mistakes = mistakes;
        this.score = score;
        this.createdAt = createdAt;
        this.game = game;
    }
}