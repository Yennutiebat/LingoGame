package bep.lingogame.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Game{
    @Id
    @GeneratedValue
    public Long id;
    @OneToOne
    Player player;
    @CreationTimestamp
    public LocalDateTime createdAt;

    public Game(){
    }

    public Game(Long id, LocalDateTime createdAt){
        this.id=id;
        this.createdAt = createdAt;
    }
}