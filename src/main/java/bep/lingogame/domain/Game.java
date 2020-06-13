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
    public String state;
    @OneToOne
    Player player;
    @CreationTimestamp
    public LocalDateTime createdAt;

    public Game(){
    }
    public Game(Long id,String state, LocalDateTime createdAt){
        this.id=id;
        this.state=state;
        this.createdAt = createdAt;
    }
    public Player getPlayer() {
        return player;
    }
    public String getState() {
        return state;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setState(String state) {
        this.state = state;
    }
}