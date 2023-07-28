import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class StageSearcherTest {
    private static Deck deck;
    private static Card board[];
    private static Card C2, C3, C4, C5, C6, C7, C8, C9, C10, CJ, CQ, CK, CA;
    private static Card S2, S3, S4, S5, S6, S7, S8, S9, S10, SJ, SQ, SK, SA;
    private static Card D2, D3, D4, D5, D6, D7, D8, D9, D10, DJ, DQ, DK, DA;
    private static Card H2, H3, H4, H5, H6, H7, H8, H9, H10, HJ, HQ, HK, HA;
    private static Player player;
    private StageSearcher searcher;
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
        player = new Player("");
        try {
            sc = new Scanner(new File("Permutations.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    @Test
    void hasPair_Flop(){
        board = new Card[]{H2, C4, D5, null, null};
        player.setCard1(C2);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(2));
    }
    @Test
    void hasPair_Flop_Board(){
        board = new Card[]{H2, C4, D4, null, null};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(2));
    }
    @Test
    void hasPair_Flop_False(){
        board = new Card[]{H2, C4, D5, null, null};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasPair(2));
    }
    @Test
    void hasPair_Turn(){
        board = new Card[]{S9, H2, C4, D5, null};
        player.setCard1(C2);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(2));
    }
    @Test
    void hasPair_Turn_Board(){
        board = new Card[]{H2, C4, D4, D8, null};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(2));
    }
    @Test
    void hasPair_Turn_False(){
        board = new Card[]{S9, H2, C4, D5, null};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasPair(2));
    }
    @Test
    void hasPair_River(){
        board = new Card[]{DJ, S9, H2, C4, D5};
        player.setCard1(C2);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(2));
    }
    @Test
    void hasPair_River_False(){
        board = new Card[]{DJ, S9, H2, C4, D5};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasPair(2));
    }
    @Test
    void hasPair_River_Board(){
        board = new Card[]{H2, C4, D4, S7, H9};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(2));
    }
    ///////////////
    @Test
    void hasThreePair_Flop(){
        board = new Card[]{H2, C4, D5, null, null};
        player.setCard1(C2);
        player.setCard2(H2);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(3));
    }
    @Test
    void hasThreePair_Flop_False(){
        board = new Card[]{H6, C4, D5, null, null};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasPair(3));
    }
    @Test
    void hasThreePair_Turn(){
        board = new Card[]{S9, H2, C2, D2, null};
        player.setCard1(C5);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(3));
    }
    @Test
    void hasThreePair_Turn_False(){
        board = new Card[]{S9, H2, C3, D5, null};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasPair(3));
    }
    @Test
    void hasThreePair_River(){
        board = new Card[]{DJ, S9, H2, C4, D5};
        player.setCard1(C2);
        player.setCard2(H2);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(3));
    }
    @Test
    void hasThreePair_River_False(){
        board = new Card[]{DJ, S9, H2, C4, D2};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasPair(3));
    }
    ///////////////
    @Test
    void hasFourPair_Flop(){
        board = new Card[]{H2, C4, D2, null, null};
        player.setCard1(C2);
        player.setCard2(H2);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(4));
    }
    @Test
    void hasFourPair_Flop_False(){
        board = new Card[]{H6, C4, D6, null, null};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasPair(4));
    }
    @Test
    void hasFourPair_Turn(){
        board = new Card[]{S2, H2, C2, D2, null};
        player.setCard1(C5);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(4));
    }
    @Test
    void hasFourPair_Turn_False(){
        board = new Card[]{S9, H3, C3, D5, null};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasPair(4));
    }
    @Test
    void hasFourPair_River(){
        board = new Card[]{D2, S2, H2, C4, D5};
        player.setCard1(C2);
        player.setCard2(H4);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasPair(4));
    }
    @Test
    void hasFourPair_River_False(){
        board = new Card[]{DJ, S9, H2, C4, D2};
        player.setCard1(C6);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasPair(4));
    }
    ///////////////
    @Test
    void hasTwoPair_Flop(){
        board = new Card[]{DJ, S9, H2, null, null};
        player.setCard1(C9);
        player.setCard2(CJ);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasTwoPair());
    }
    @Test
    void hasTwoPair_Flop_False(){
        board = new Card[]{DJ, S9, H2, null, null};
        player.setCard1(C3);
        player.setCard2(CJ);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasTwoPair());
    }
    @Test
    void hasTwoPair_Turn(){
        board = new Card[]{DJ, S9, H2, H9, null};
        player.setCard1(C3);
        player.setCard2(CJ);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasTwoPair());
    }
    @Test
    void hasTwoPair_Turn_False(){
        board = new Card[]{DJ, S9, H2, H9, null};
        player.setCard1(C3);
        player.setCard2(C5);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasTwoPair());
    }
    @Test
    void hasTwoPair_River(){
        board = new Card[]{D8, S9, H2, H8, C4};
        player.setCard1(CJ);
        player.setCard2(CJ);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasTwoPair());
    }
    @Test
    void hasTwoPair_River_False(){
        board = new Card[]{DJ, S9, H2, H8, H6};
        player.setCard1(C3);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasTwoPair());
    }
    //////////////
    @Test
    void hasStraight_Flop(){
        board = new Card[]{DJ, S9, H10, null, null};
        player.setCard1(C8);
        player.setCard2(C7);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraight());
    }
    @Test
    void hasStraight_Flop_5High(){
        board = new Card[]{D4, S2, HA, null, null};
        player.setCard1(C3);
        player.setCard2(C5);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraight());
    }
    @Test
    void hasStraight_Flop_False(){
        board = new Card[]{DJ, S9, H2, null, null};
        player.setCard1(C8);
        player.setCard2(C10);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasStraight());
    }
    @Test
    void hasStraight_Turn(){
        board = new Card[]{DJ, S9, H2, H10, null};
        player.setCard1(CQ);
        player.setCard2(C8);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraight());
    }
    @Test
    void hasStraight_Turn_5High(){
        board = new Card[]{D4, S9, H2, H5, null};
        player.setCard1(CA);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraight());
    }
    @Test
    void hasStraight_Turn_False(){
        board = new Card[]{DQ, S9, H2, H10, null};
        player.setCard1(C7);
        player.setCard2(C8);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasStraight());
    }
    @Test
    void hasStraight_River(){
        board = new Card[]{D8, S6, H2, H5, C4};
        player.setCard1(C9);
        player.setCard2(C7);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraight());
    }
    @Test
    void hasStraight_River_5High(){
        board = new Card[]{D8, S9, H2, HA, C4};
        player.setCard1(C3);
        player.setCard2(C5);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraight());
    }
    @Test
    void hasStraight_River_False(){
        board = new Card[]{D10, S9, H8, H5, H6};
        player.setCard1(C3);
        player.setCard2(C4);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasStraight());
    }
    ///////////////
    @Test
    void hasFlush_Flop(){
        board = new Card[]{SJ, S9, S2, null, null};
        player.setCard1(S10);
        player.setCard2(SQ);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasFlush());
    }
    @Test
    void hasFlush_Flop_False(){
        board = new Card[]{SJ, S9, S2, null, null};
        player.setCard1(S10);
        player.setCard2(DQ);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasFlush());
    }
    @Test
    void hasFlush_Turn(){
        board = new Card[]{SJ, S9, S2, D4, null};
        player.setCard1(S10);
        player.setCard2(SQ);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasFlush());
    }
    @Test
    void hasFlush_Turn_False(){
        board = new Card[]{SJ, S9, S2, D4, null};
        player.setCard1(S10);
        player.setCard2(DQ);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasFlush());
    }
    @Test
    void hasFlush_River(){
        board = new Card[]{SJ, H9, S2, S4, S5};
        player.setCard1(S10);
        player.setCard2(SQ);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasFlush());
    }
    @Test
    void hasFlush_River_False(){
        board = new Card[]{SJ, H9, S2, S4, D5};
        player.setCard1(S10);
        player.setCard2(DQ);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasFlush());
    }
    //////////////

    @Test
    void hasStraightFlush_Flop(){
        board = new Card[]{CJ, C9, C10, null, null};
        player.setCard1(C8);
        player.setCard2(C7);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraightFlush());
    }
    @Test
    void hasStraightFlush_Flop_5High(){
        board = new Card[]{S4, S2, SA, null, null};
        player.setCard1(S3);
        player.setCard2(S5);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraightFlush());
    }
    @Test
    void hasStraightFlush_Flop_False(){
        board = new Card[]{S4, H2, SA, null, null};
        player.setCard1(S3);
        player.setCard2(S5);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasStraightFlush());
    }
    @Test
    void hasStraightFlush_Turn(){
        board = new Card[]{SJ, S9, H2, S10, null};
        player.setCard1(SQ);
        player.setCard2(S8);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraightFlush());
    }
    @Test
    void hasStraightFlush_Turn_5High(){
        board = new Card[]{C4, S9, C2, C5, null};
        player.setCard1(CA);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraightFlush());
    }
    @Test
    void hasStraightFlush_Turn_False(){
        board = new Card[]{C4, S9, C2, D5, null};
        player.setCard1(CA);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasStraightFlush());
    }
    @Test
    void hasStraightFlush_River(){
        board = new Card[]{C8, C6, H2, C5, C4};
        player.setCard1(C9);
        player.setCard2(C7);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraightFlush());
    }
    @Test
    void hasStraightFlush_River_5High(){
        board = new Card[]{D8, S9, C2, CA, C4};
        player.setCard1(C3);
        player.setCard2(C5);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasStraightFlush());
    }
    @Test
    void hasStraightFlush_River_False(){
        board = new Card[]{C8, H6, H2, C5, C4};
        player.setCard1(C9);
        player.setCard2(C7);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasStraightFlush());
    }
    ///////////////
    @Test
    void hasFullHouse_Flop(){
        board = new Card[]{DJ, S9, H9, null, null};
        player.setCard1(C9);
        player.setCard2(CJ);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasFullHouse());
    }
    @Test
    void hasFullHouse_Flop_False(){
        board = new Card[]{DJ, S9, H3, null, null};
        player.setCard1(C3);
        player.setCard2(CJ);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasFullHouse());
    }
    @Test
    void hasFullHouse_Turn(){
        board = new Card[]{DJ, S9, H2, H9, null};
        player.setCard1(C9);
        player.setCard2(CJ);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasFullHouse());
    }
    @Test
    void hasFullHouse_Turn_False(){
        board = new Card[]{DJ, S9, H2, H9, null};
        player.setCard1(C3);
        player.setCard2(C9);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasFullHouse());
    }
    @Test
    void hasFullHouse_River(){
        board = new Card[]{D8, S9, H8, H8, C4};
        player.setCard1(CJ);
        player.setCard2(CJ);
        searcher = new StageSearcher(board, player);
        assertTrue(searcher.hasFullHouse());
    }
    @Test
    void hasFullHouse_River_False(){
        board = new Card[]{DJ, S9, H3, H8, H6};
        player.setCard1(C3);
        player.setCard2(C3);
        searcher = new StageSearcher(board, player);
        assertFalse(searcher.hasFullHouse());
    }
*/
}
