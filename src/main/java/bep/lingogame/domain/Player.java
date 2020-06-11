package bep.lingogame.domain;


import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Player {
    @Id
    @GeneratedValue
    public Long id;
    public String name;
    public int score;
    @OneToOne
    Game game;
    @CreationTimestamp
    public LocalDateTime createdAt;

    public Player() {
    }

    public Player(Long id, String name, int score, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.createdAt = createdAt;
    }
}