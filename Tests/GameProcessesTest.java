import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameProcessesTest {

    private static Deck deck;
    private static Card board[];
    private static Card C2, C3, C4, C5, C6, C7, C8, C9, C10, CJ, CQ, CK, CA;
    private static Card S2, S3, S4, S5, S6, S7, S8, S9, S10, SJ, SQ, SK, SA;
    private static Card D2, D3, D4, D5, D6, D7, D8, D9, D10, DJ, DQ, DK, DA;
    private static Card H2, H3, H4, H5, H6, H7, H8, H9, H10, HJ, HQ, HK, HA;
    private static Card card1, card2;
    private static Player p1, p2, p3, p4;
    private static Table table;
    private static ArrayList<Player> players;
    private static HandComparer comparer;
    private static ArrayList<Card[]> permutations;
    private static Scanner sc;
    @BeforeAll
    static void init(){
        deck = new Deck();
        //deal cards
        C2 = deck.deal(); C3 = deck.deal(); C4 = deck.deal(); C5 = deck.deal(); C6 = deck.deal(); C7 = deck.deal();
        C8 = deck.deal(); C9 = deck.deal(); C10 = deck.deal(); CJ = deck.deal(); CQ = deck.deal(); CK = deck.deal(); CA = deck.deal();
        S2 = deck.deal(); S3 = deck.deal(); S4 = deck.deal(); S5 = deck.deal(); S6 = deck.deal(); S7 = deck.deal();
        S8 = deck.deal(); S9 = deck.deal(); S10 = deck.deal(); SJ = deck.deal(); SQ = deck.deal(); SK = deck.deal(); SA = deck.deal();
        H2 = deck.deal(); H3 = deck.deal(); H4 = deck.deal(); H5 = deck.deal(); H6 = deck.deal(); H7 = deck.deal();
        H8 = deck.deal(); H9 = deck.deal(); H10 = deck.deal(); HJ = deck.deal(); HQ = deck.deal(); HK = deck.deal(); HA = deck.deal();
        D2 = deck.deal(); D3 = deck.deal(); D4 = deck.deal(); D5 = deck.deal(); D6 = deck.deal(); D7 = deck.deal();
        D8 = deck.deal(); D9 = deck.deal(); D10 = deck.deal(); DJ = deck.deal(); DQ = deck.deal(); DK = deck.deal(); DA = deck.deal();
        permutations = new ArrayList<>();
        board = new Card[5];
        p1 = new Player("P1");
        p2 = new Player("P2");
        p3 = new Player("P3");
        p4 = new Player("P4");
        p1.setMoney(100);
        p2.setMoney(100);
        p3.setMoney(100);
        p4.setMoney(100);
        players = new ArrayList<>(List.of(p1, p2, p3, p4));
        table = new Table(board, 0, players, null);
    }
    //region Side Pot Tests
    @Test
    void singleSidePot(){
        p1.setMoney(50);
        p1.setBet(50);
        p1.setAllIn(true);
        p2.setBet(80);
        p3.setBet(80);
        p4.setBet(0);
        p4.setFolded(true);
        ArrayList<SidePot> results = GameProcesses.createSidePots(table);
        assertTrue(results.size() == 1);
        assertTrue(results.get(0).getPlayers().size() == 3);
        assertTrue(results.get(0).getPot() == 150);
        assertTrue(p1.getMoney() == 0);
        assertTrue(p2.getMoney() == 50 && p2.getBet() == 30);
        assertTrue(p3.getMoney() == 50 && p3.getBet() == 30);
        assertTrue(p4.getMoney() == 100);

    }
    //endregion

}