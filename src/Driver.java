import java.util.*;


public class Driver{
    public static void main(String[] args){
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            players.add(new Player("Player " + String.valueOf(i)));
        }
        Save save = new Save(players, 3, "On My iPhone//Downloads//save.ser");
        
    }

    public static void assertTrue(boolean tf){
        System.out.println(String.valueOf(tf));
    }
}