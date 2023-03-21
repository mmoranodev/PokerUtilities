import java.util.ArrayList;

public class Table {
    private Card[] board;
    private int pot;
    private ArrayList<Player> players;
    private ArrayList<SidePot> sidePots;
    public Table(){}
    public Table(Card[] board, int pot, ArrayList<Player> players, ArrayList<SidePot> sidePots){
        this.board = board;
        this.pot = pot;
        this.players = players;
        this.sidePots = sidePots;
    }

    public Card[] getBoard() {
        return board;
    }

    public void setBoard(Card[] board) {
        this.board = board;
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

    public ArrayList<SidePot> getSidePots() {
        return sidePots;
    }

    public void setSidePots(ArrayList<SidePot> sidePots) {
        this.sidePots = sidePots;
    }
}
