package models;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int gameid;
    private List<Round> rounds= new ArrayList<>();

    public Game(int gameid){
        this.gameid= gameid;
    }
}
