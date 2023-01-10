import java.util.ArrayList;

public class SidePot {
    private int pot;
    private ArrayList<Player> players;

    public SidePot(ArrayList<Player> players, int pot)
    {
        this.players = players;
        this.pot = pot;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
