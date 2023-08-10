import java.io.Serializable;
import java.util.ArrayList;

public class Save implements Serializable {
    private ArrayList<Player> players;
    private int dealerPos;
    private String outputLocation;

    public Save(ArrayList<Player> players, int dealerPos, String outputLocation){
        this.players = players;
        this.dealerPos = dealerPos;
        this.outputLocation = outputLocation;
    }
    public Save(){}

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
    public void setOutputLocation(String outputLocation){
        this.outputLocation = outputLocation;
    }
    public String getOutputLocation(){
        return outputLocation;
    }
}
