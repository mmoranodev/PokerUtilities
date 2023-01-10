import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class BoardSearcherTest {
    private static Deck deck;
    private static Card temp;
    private static Card board[]=new Card[5];
    private static Card C2, C3, C4, C5, C6, C7, C8, C9, C10, CJ, CQ, CK, CA;
    private static Card S2, S3, S4, S5, S6, S7, S8, S9, S10, SJ, SQ, SK, SA;
    private static Card D2, D3, D4, D5, D6, D7, D8, D9, deck0, DJ, DQ, DK, DA;
    private static Card H2, H3, H4, H5, H6, H7, H8, H9, H10, HJ, HQ, HK, HA;
    private static BoardSearcher searcher;
    @BeforeAll
    static void init(){
        temp = new Card();
        deck = new Deck();
        //deal cards
        C2 = deck.deal(); C3 = deck.deal(); C4 = deck.deal(); C5 = deck.deal(); C6 = deck.deal(); C7 = deck.deal();
        C8 = deck.deal(); C9 = deck.deal(); C10 = deck.deal(); CJ = deck.deal(); CQ = deck.deal(); CK = deck.deal(); CA = deck.deal();
        S2 = deck.deal(); S3 = deck.deal(); S4 = deck.deal(); S5 = deck.deal(); S6 = deck.deal(); S7 = deck.deal();
        S8 = deck.deal(); S9 = deck.deal(); S10 = deck.deal(); SJ = deck.deal(); SQ = deck.deal(); SK = deck.deal(); SA = deck.deal();
        D2 = deck.deal(); D3 = deck.deal(); D4 = deck.deal(); D5 = deck.deal(); D6 = deck.deal(); D7 = deck.deal();
        D8 = deck.deal(); D9 = deck.deal(); deck0 = deck.deal(); DJ = deck.deal(); DQ = deck.deal(); DK = deck.deal(); DA = deck.deal();
        H2 = deck.deal(); H3 = deck.deal(); H4 = deck.deal(); H5 = deck.deal(); H6 = deck.deal(); H7 = deck.deal();
        H8 = deck.deal(); H9 = deck.deal(); H10 = deck.deal(); HJ = deck.deal(); HQ = deck.deal(); HK = deck.deal(); HA = deck.deal();

    }

    @Test
    void allBoardConfig() {
        ArrayList<String> results = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            Card temp = board[i];
            board[i] = board[(i+1)%5];
            board[(i+1)%5] = temp;
        }
    }

    @Test
    void pairPresent() {
        getCombinations();
    }

    private ArrayList<Card[]> getCombinations()
    {
        ArrayList<Card[]> results = new ArrayList<>();
        Card[] board = new Card[]{C2,C3,C4,C5,C6};
        Card temp;
        int counta = 0, countb = 0, countc = 0, countd = 0, counte = 0;

        return results;
    }

}