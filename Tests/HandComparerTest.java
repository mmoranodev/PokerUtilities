import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HandComparerTest {
    private static Deck deck;
    private static Card board[];
    private static Card C2, C3, C4, C5, C6, C7, C8, C9, C10, CJ, CQ, CK, CA;
    private static Card S2, S3, S4, S5, S6, S7, S8, S9, S10, SJ, SQ, SK, SA;
    private static Card D2, D3, D4, D5, D6, D7, D8, D9, D10, DJ, DQ, DK, DA;
    private static Card H2, H3, H4, H5, H6, H7, H8, H9, H10, HJ, HQ, HK, HA;
    private static Card card1, card2;
    private static Player p1, p2, p3;
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
        p1.setMoney(100);
        p2.setMoney(100);
        p3.setMoney(100);
        players = new ArrayList<>(List.of(p1, p2, p3));
        table = new Table(board, 0, players, null);
        try {
            sc = new Scanner(new File("Permutations.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static void getPermutations(Card[] board)
    {
        while(sc.hasNextLine())
        {
            String[] line = sc.nextLine().split(",");
            int[] nums = new int[5];
            for(int i = 0; i < line.length; i++){
                nums[i] = Integer.parseInt(line[i]);
            }
            permutations.add(new Card[]{board[nums[0]-1],board[nums[1]-1],board[nums[2]-1],board[nums[3]-1],board[nums[4]-1]});
        }
    }
    //methods used in commented out tests made private
    //region CompareHand Tests
    /*
    @Test
    void compareHighCard_P1(){
        board = new Card[]{H3, C9, C10, CJ, CA};
        p1.setCard1(CQ);
        p1.setCard2(C2);
        p2.setCard1(C4);
        p2.setCard2(C2);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareHighCard(p1, p2) == 1;
            if(!result) {
                System.out.println(result);
                comparer.compareHighCard(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareHighCard_P2(){
        board = new Card[]{H3, C9, C10, CJ, CA};
        p1.setCard1(CQ);
        p1.setCard2(C2);
        p2.setCard1(C4);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareHighCard(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.compareHighCard(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareHighCard_Tie(){
        board = new Card[]{HK, C9, C10, CJ, CA};
        p1.setCard1(C3);
        p1.setCard2(C2);
        p2.setCard1(C4);
        p2.setCard2(C3);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareHighCard(p1, p2) == 0;
            if(!result) {
                System.out.println(result);
                comparer.compareHighCard(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareStraight_P1(){
        board = new Card[]{H4, C9, C3, C2, CA};
        p1.setCard1(C5);
        p1.setCard2(C6);
        p2.setCard1(C9);
        p2.setCard2(C5);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareStraight(p1, p2) ==1;
            if(!result) {
                System.out.println(result);
                comparer.compareStraight(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareStraight_P2(){
        board = new Card[]{H10, CJ, C3, C2, CA};
        p1.setCard1(C5);
        p1.setCard2(C4);
        p2.setCard1(CQ);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareStraight(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.compareStraight(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareStraight_Tie(){
        board = new Card[]{H10, CJ, C3, CK, CA};
        p1.setCard1(C5);
        p1.setCard2(CQ);
        p2.setCard1(CQ);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareStraight(p1, p2) == 0;
            if(!result) {
                System.out.println(result);
                comparer.compareStraight(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareStraight_AceHigh_P1(){
        board = new Card[]{H10, C2, C3, CK, CA};
        p1.setCard1(C5);
        p1.setCard2(C4);
        p2.setCard1(CQ);
        p2.setCard2(CJ);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareStraight(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.compareStraight(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFourPair_P1(){
        board = new Card[]{H2, CK, C3, CK, CK};
        p1.setCard1(CK);
        p1.setCard2(CQ);
        p2.setCard1(C5);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFourPair(p1, p2) == 1;
            if(!result) {
                System.out.println(result);
                comparer.compareFourPair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFourPair_P2(){
        board = new Card[]{H2, CK, C3, CK, CK};
        p1.setCard1(CK);
        p1.setCard2(C5);
        p2.setCard1(CQ);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFourPair(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.compareFourPair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFourPair_Tie(){
        board = new Card[]{H10, CK, C3, CK, CK};
        p1.setCard1(CK);
        p1.setCard2(C5);
        p2.setCard1(C9);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFourPair(p1, p2) == 0;
            if(!result) {
                System.out.println(result);
                comparer.compareFourPair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareThreePair_P1(){
        board = new Card[]{H2, CK, C3, C4, CK};
        p1.setCard1(CK);
        p1.setCard2(CQ);
        p2.setCard1(C5);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareThreePair(p1, p2) == 1;
            if(!result) {
                System.out.println(result);
                comparer.compareThreePair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareThreePair_P2(){
        board = new Card[]{H2, C4, C3, CK, CK};
        p1.setCard1(CK);
        p1.setCard2(C5);
        p2.setCard1(CQ);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareThreePair(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.compareThreePair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareThreePair_Tie(){
        board = new Card[]{H10, CJ, C3, CK, CK};
        p1.setCard1(CK);
        p1.setCard2(C5);
        p2.setCard1(C9);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareThreePair(p1, p2) == 0;
            if(!result) {
                System.out.println(result);
                comparer.compareThreePair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void comparePair_P1(){
        board = new Card[]{H2, C10, C3, CJ, CK};
        p1.setCard1(CK);
        p1.setCard2(CQ);
        p2.setCard1(C4);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.comparePair(p1, p2) == 1;
            if(!result) {
                System.out.println(result);
                comparer.comparePair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void comparePair_P2(){
        board = new Card[]{H2, C10, C3, CJ, CK};
        p1.setCard1(CK);
        p1.setCard2(C5);
        p2.setCard1(CQ);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.comparePair(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.comparePair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void comparePair_Tie(){
        board = new Card[]{H9, C10, C3, CJ, CK};
        p1.setCard1(CK);
        p1.setCard2(C5);
        p2.setCard1(C5);
        p2.setCard2(CK);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.comparePair(p1, p2) == 0;
            if(!result) {
                System.out.println(result);
                comparer.comparePair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFlush_P1(){
        board = new Card[]{H3, H5, H7, H9, C2};
        p1.setCard1(H10);
        p1.setCard2(C5);
        p2.setCard1(C5);
        p2.setCard2(H4);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFlush(p1, p2) == 1;
            if(!result) {
                System.out.println(result);
                comparer.compareFlush(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFlush_P2(){
        board = new Card[]{H3, H5, H7, H9, C2};
        p1.setCard1(H4);
        p1.setCard2(C5);
        p2.setCard1(H4);
        p2.setCard2(H8);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFlush(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.compareFlush(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFlush_Tie(){
        board = new Card[]{H3, H5, H7, H9, H8};
        p1.setCard1(H2);
        p1.setCard2(C5);
        p2.setCard1(H2);
        p2.setCard2(H2);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFlush(p1, p2) == 0;
            if(!result) {
                System.out.println(result);
                comparer.compareFlush(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFullHouse_P1_Three(){
        board = new Card[]{H3, H5, H7, H7, H3};
        p1.setCard1(H7);
        p1.setCard2(C5);
        p2.setCard1(H5);
        p2.setCard2(H3);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFullHouse(p1, p2) == 1;
            if(!result) {
                System.out.println(result);
                comparer.compareFullHouse(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFullHouse_P1_Two(){
        board = new Card[]{H3, H5, H7, H7, H2};
        p1.setCard1(H7);
        p1.setCard2(C5);
        p2.setCard1(H7);
        p2.setCard2(H3);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFullHouse(p1, p2) == 1;
            if(!result) {
                System.out.println(result);
                comparer.compareFullHouse(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFullHouse_P2_Three(){
        board = new Card[]{H3, H5, H7, H7, H5};
        p1.setCard1(H3);
        p1.setCard2(C5);
        p2.setCard1(H7);
        p2.setCard2(H3);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFullHouse(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.compareFullHouse(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFullHouse_P2_Two(){
        board = new Card[]{H3, H5, H7, H7, H2};
        p1.setCard1(H7);
        p1.setCard2(C3);
        p2.setCard1(H5);
        p2.setCard2(H7);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFullHouse(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.compareFullHouse(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareFullHouse_Tie(){
        board = new Card[]{H3, H5, H7, H7, H2};
        p1.setCard1(H7);
        p1.setCard2(C5);
        p2.setCard1(H5);
        p2.setCard2(H7);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareFullHouse(p1, p2) == 0;
            if(!result) {
                System.out.println(result);
                comparer.compareFullHouse(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareTwoPair_P1(){
        board = new Card[]{H3, H5, H7, H9, H2};
        p1.setCard1(H7);
        p1.setCard2(C5);
        p2.setCard1(H5);
        p2.setCard2(H3);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareTwoPair(p1, p2) == 1;
            if(!result) {
                System.out.println(result);
                comparer.compareTwoPair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareTwoPair_P2(){
        board = new Card[]{H2, H5, H7, H9, H2};
        p1.setCard1(H3);
        p1.setCard2(C3);
        p2.setCard1(H5);
        p2.setCard2(H7);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareTwoPair(p1, p2) == 2;
            if(!result) {
                System.out.println(result);
                comparer.compareTwoPair(p1, p2);
            }
            assertTrue(result);
        }
    }
    @Test
    void compareTwoPair_Tie(){
        board = new Card[]{H6, H4, H7, H9, H6};
        p1.setCard1(H4);
        p1.setCard2(C9);
        p2.setCard1(H5);
        p2.setCard2(H9);
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            table.setBoard(permutation);
            comparer = new HandComparer(table);
            result = result && comparer.compareTwoPair(p1, p2) == 0;
            if(!result) {
                System.out.println(result);
                comparer.compareTwoPair(p1, p2);
            }
            assertTrue(result);
        }
    }
*/

    //endregion
    @Test
    void findWinner_P1(){
        board = new Card[]{H4, C9, C3, C2, CA};
        p1.setCard1(C5);
        p1.setCard2(C6);
        p2.setCard1(C9);
        p2.setCard2(C4);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p1.setHandRank(HandRanker.HandRank.Straight);
        p2.setHandRank(HandRanker.HandRank.TwoPair);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 1);
        assertTrue(result.get(0).getName().equals("P1"));
        assertTrue(result.get(0).getMoney() == 110);
    }
    @Test
    void findWinner_P2(){
        board = new Card[]{H4, C9, C3, C2, CA};
        p2.setCard1(C5);
        p2.setCard2(C6);
        p1.setCard1(C9);
        p1.setCard2(C4);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p2.setHandRank(HandRanker.HandRank.Straight);
        p1.setHandRank(HandRanker.HandRank.TwoPair);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 1);
        assertTrue(result.get(0).getName().equals("P2"));
        assertTrue(result.get(0).getMoney() == 110);
    }
    @Test
    void findWinner_Tie(){
        board = new Card[]{H4, C9, C3, C2, CA};
        p2.setCard1(C5);
        p2.setCard2(C6);
        p1.setCard1(C6);
        p1.setCard2(C5);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p2.setHandRank(HandRanker.HandRank.Straight);
        p1.setHandRank(HandRanker.HandRank.Straight);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 2);
        assertTrue(result.get(0).getMoney() == 105);
        assertTrue(result.get(1).getMoney() == 105);
    }
    @Test
    void findWinner_3Player_P1(){
        board = new Card[]{H2, C4, D6, S8, DJ};
        p1.setHand(C6, S8);
        p2.setHand(C2, S8);
        p3.setHand(C4, S8);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p1.setHandRank(HandRanker.HandRank.TwoPair);
        p2.setHandRank(HandRanker.HandRank.TwoPair);
        p3.setHandRank(HandRanker.HandRank.TwoPair);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 1);
        assertTrue(result.get(0).getName().equals("P1"));
        assertTrue(result.get(0).getMoney() == 110);
    }
    @Test
    void findWinner_3Player_P2(){
        board = new Card[]{H2, C4, D6, S8, DJ};
        p2.setHand(C6, S8);
        p1.setHand(C2, S8);
        p3.setHand(C4, S8);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p1.setHandRank(HandRanker.HandRank.TwoPair);
        p2.setHandRank(HandRanker.HandRank.TwoPair);
        p3.setHandRank(HandRanker.HandRank.TwoPair);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 1);
        assertTrue(result.get(0).getName().equals("P2"));
        assertTrue(result.get(0).getMoney() == 110);
    }
    @Test
    void findWinner_3Player_P3(){
        board = new Card[]{H2, C4, D6, S8, DJ};
        p3.setHand(C6, S8);
        p1.setHand(C2, S8);
        p2.setHand(C4, S8);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p1.setHandRank(HandRanker.HandRank.TwoPair);
        p2.setHandRank(HandRanker.HandRank.TwoPair);
        p3.setHandRank(HandRanker.HandRank.TwoPair);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 1);
        assertTrue(result.get(0).getName().equals("P3"));
        assertTrue(result.get(0).getMoney() == 110);
    }
    @Test
    void findWinner_3Player_P1P2_Tie(){
        board = new Card[]{H2, C4, D6, S8, DJ};
        p1.setHand(C6, S8);
        p2.setHand(C6, S8);
        p3.setHand(C4, S8);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p1.setHandRank(HandRanker.HandRank.TwoPair);
        p2.setHandRank(HandRanker.HandRank.TwoPair);
        p3.setHandRank(HandRanker.HandRank.TwoPair);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 2);
        ArrayList<String> names = new ArrayList<>(List.of(result.get(0).getName(), result.get(1).getName()));
        assertTrue(names.contains("P1"));
        assertTrue(names.contains("P2"));
        assertTrue(result.get(0).getMoney() == 105);
        assertTrue(result.get(1).getMoney() == 105);
    }
    @Test
    void findWinner_3Player_P1P3_Tie(){
        board = new Card[]{H2, C4, D6, S8, DJ};
        p1.setHand(C6, S8);
        p3.setHand(C6, S8);
        p2.setHand(C4, S8);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p1.setHandRank(HandRanker.HandRank.TwoPair);
        p2.setHandRank(HandRanker.HandRank.TwoPair);
        p3.setHandRank(HandRanker.HandRank.TwoPair);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 2);
        ArrayList<String> names = new ArrayList<>(List.of(result.get(0).getName(), result.get(1).getName()));
        assertTrue(names.contains("P1"));
        assertTrue(names.contains("P3"));
        assertTrue(result.get(0).getMoney() == 105);
        assertTrue(result.get(1).getMoney() == 105);
    }
    @Test
    void findWinner_3Player_P2P3_Tie(){
        board = new Card[]{H2, C4, D6, S8, DJ};
        p2.setHand(C6, S8);
        p3.setHand(C6, S8);
        p1.setHand(C4, S8);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p1.setHandRank(HandRanker.HandRank.TwoPair);
        p2.setHandRank(HandRanker.HandRank.TwoPair);
        p3.setHandRank(HandRanker.HandRank.TwoPair);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 2);
        ArrayList<String> names = new ArrayList<>(List.of(result.get(0).getName(), result.get(1).getName()));
        assertTrue(names.contains("P2"));
        assertTrue(names.contains("P3"));
        assertTrue(result.get(0).getMoney() == 105);
        assertTrue(result.get(1).getMoney() == 105);
    }
    @Test
    void findWinner_3Player_Tie(){
        board = new Card[]{H2, C4, D6, S8, DJ};
        p2.setHand(C6, S8);
        p3.setHand(C6, S8);
        p1.setHand(C6, S8);
        table.setBoard(board);
        int pot = 10;
        table.setPot(pot);
        p1.setHandRank(HandRanker.HandRank.TwoPair);
        p2.setHandRank(HandRanker.HandRank.TwoPair);
        p3.setHandRank(HandRanker.HandRank.TwoPair);
        comparer = new HandComparer(table);
        ArrayList<Player> result = comparer.findWinner();
        assertTrue(result.size() == 3);
        ArrayList<String> names = new ArrayList<>(List.of(result.get(0).getName(), result.get(1).getName(), result.get(2).getName()));
        assertTrue(names.contains("P1"));
        assertTrue(names.contains("P2"));
        assertTrue(names.contains("P3"));
        assertTrue(result.get(0).getMoney() == 103);
        assertTrue(result.get(1).getMoney() == 103);
        assertTrue(result.get(2).getMoney() == 103);
    }
    //TODO add tests to compare players with same hands but one distinct winner, do this with three players
}