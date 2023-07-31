import java.util.ArrayList;
import java.util.Collections;

public class GameProcesses {
    public static ArrayList<SidePot> createSidePots(Table table){
        ArrayList<SidePot> results = new ArrayList<>();
        ArrayList<Player> sidePlayers = new ArrayList<>();
        ArrayList<Player> eligiblePlayers = new ArrayList<>();
        for(Player player : table.getPlayers()) {
            if (player.isAllIn() && !player.inSidePot())
                sidePlayers.add(player);
            if(!player.hasFolded())
                eligiblePlayers.add(player);
        }
        Collections.sort(sidePlayers, (p1, p2) -> Integer.compare(p1.getBet(), p2.getBet()));
        for(int i = 0; i < sidePlayers.size(); i++) {
            if(sidePlayers.get(i).getBet() > 0) {
                results.add(new SidePot(eligiblePlayers,
                        getSidePotAmount(eligiblePlayers, sidePlayers.get(i).getBet())));
            }
        }
        return results;
    }
    private static int getSidePotAmount(ArrayList<Player> players, int sideAmount){
        int amount = 0;
        for(Player player : players){
            if(player.hasFolded() || player.inSidePot())
                continue;
            amount += sideAmount;
            player.setMoney(player.getMoney() - sideAmount);
            player.setBet(player.getBet() - sideAmount);
        }
        return amount;
    }
}
