import java.io.Serializable;
import java.util.ArrayList;


public class Save implements Serializable {
    private ArrayList<Player> players;
    private int dealerPos;

    public Save(ArrayList<Player> players, int dealerPos){
        this.players = players;
        this.dealerPos = dealerPos;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getDealerPos() {
        return dealerPos;
    }

    public void setDealerPos(int dealerPos) {
        this.dealerPos = dealerPos;
    }
}
