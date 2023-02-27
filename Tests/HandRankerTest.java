import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HandRankerTest {
    private static Deck deck;
    private static Card board[];
    private static Card C2, C3, C4, C5, C6, C7, C8, C9, C10, CJ, CQ, CK, CA;
    private static Card S2, S3, S4, S5, S6, S7, S8, S9, S10, SJ, SQ, SK, SA;
    private static Card D2, D3, D4, D5, D6, D7, D8, D9, D10, DJ, DQ, DK, DA;
    private static Card H2, H3, H4, H5, H6, H7, H8, H9, H10, HJ, HQ, HK, HA;
    private static Card card1, card2;
    private static HandRanker ranker;
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
    @Test
    void hasPair_C1() {
        board = new Card[]{H3, C9, C4, C5, C6};
        card1 = D3;
        card2 = H8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasPair();
            if(!result) {
                System.out.println(result);
                ranker.hasPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasPair_C2() {
        board = new Card[]{H3, C9, C4, C5, C6};
        card2 = D3;
        card1 = H8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card2, card1);
            result = result && ranker.hasPair();
            if(!result) {
                System.out.println(result);
                ranker.hasPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasPair_Pocket() {
        board = new Card[]{H3, C9, C4, C5, C6};
        card2 = D8;
        card1 = H8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card2, card1);
            result = result && ranker.hasPair();
            if(!result) {
                System.out.println(result);
                ranker.hasPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasPair_Board() {
        board = new Card[]{H3, C9, D9, C5, C6};
        card2 = D10;
        card1 = H8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card2, card1);
            result = result && ranker.hasPair();
            if(!result) {
                System.out.println(result);
                ranker.hasPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasPair_False() {
        board = new Card[]{H3, C9, DJ, C5, C6};
        card2 = D10;
        card1 = H8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card2, card1);
            result = result && ranker.hasPair();
            if(!result) {
                System.out.println(result);
                ranker.hasPair();
            }
            assertFalse(result);
        }
    }

    @Test
    void hasTwoPair() {
        board = new Card[]{H3, H5, C9, S10, DK};
        card1 = D3;
        card2 = D5;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasTwoPair();
            if(!result){
                System.out.println(result);
                ranker.hasTwoPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasTwoPair_False() {
        board = new Card[]{H3, H5, C9, S10, DK};
        card1 = D5;
        card2 = D5;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasTwoPair();
            if(!result){
                System.out.println(result);
                ranker.hasTwoPair();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasTwoPair_OneBoard_C2() {
        board = new Card[]{H3, D3, C5, S10, DK};
        card1 = D7;
        card2 = D5;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasTwoPair();
            if(!result){
                System.out.println(result);
                ranker.hasTwoPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasTwoPair_OneBoard_C1() {
        board = new Card[]{H3, D3, C5, S10, DK};
        card1 = D5;
        card2 = D7;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasTwoPair();
            if(!result){
                System.out.println(result);
                ranker.hasTwoPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasTwoPair_OneBoard_False() {
        board = new Card[]{H3, D3, C9, S10, DK};
        card1 = D7;
        card2 = D5;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasTwoPair();
            if(!result){
                System.out.println(result);
                ranker.hasTwoPair();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasTwoPair_Pocket() {
        board = new Card[]{H3, C3, C9, S10, DK};
        card1 = D5;
        card2 = S5;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasTwoPair();
            if(!result){
                System.out.println(result);
                ranker.hasTwoPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasTwoPair_Pocket_False() {
        board = new Card[]{H3, C4, C9, S10, DK};
        card1 = D5;
        card2 = S5;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasTwoPair();
            if(!result){
                System.out.println(result);
                ranker.hasTwoPair();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasTwoPair_TwoBoard() {
        board = new Card[]{H5, D5, C3, S3, DK};
        card1 = D8;
        card2 = D4;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasTwoPair();
            if(!result){
                System.out.println(result);
                ranker.hasTwoPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasTwoPair_TwoBoard_False() {
        board = new Card[]{H5, D5, C2, S3, DK};
        card1 = D8;
        card2 = D4;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasTwoPair();
            if(!result){
                System.out.println(result);
                ranker.hasTwoPair();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasThreePair_Board(){
        board = new Card[]{H8, D8, C9, S8, DK};
        card1 = D9;
        card2 = SK;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasThreePair();
            if(!result){
                System.out.println(result);
                ranker.hasThreePair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasThreePair_Pocket(){
        board = new Card[]{H8, D5, C9, S3, DK};
        card1 = D8;
        card2 = S8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasThreePair();
            if(!result){
                System.out.println(result);
                ranker.hasThreePair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasThreePair_C1(){
        board = new Card[]{H8, D5, C8, S3, DK};
        card1 = D8;
        card2 = D4;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasThreePair();
            if(!result){
                System.out.println(result);
                ranker.hasThreePair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasThreePair_C1_False(){
        board = new Card[]{H8, D5, C7, S3, DK};
        card1 = D8;
        card2 = D4;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasThreePair();
            if(!result){
                System.out.println(result);
                ranker.hasThreePair();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasThreePair_C2(){
        board = new Card[]{H8, D5, C8, S3, DK};
        card2 = D8;
        card1 = D4;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasThreePair();
            if(!result){
                System.out.println(result);
                ranker.hasThreePair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFourPair_Board(){
        board = new Card[]{H8, D8, C8, S8, DK};
        card1 = D9;
        card2 = SK;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFourPair();
            if(!result){
                System.out.println(result);
                ranker.hasFourPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFourPair_Pocket(){
        board = new Card[]{H8, D5, C8, S3, DK};
        card1 = D8;
        card2 = S8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFourPair();
            if(!result){
                System.out.println(result);
                ranker.hasFourPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFourPair_C1(){
        board = new Card[]{H8, D5, C8, S8, DK};
        card1 = D8;
        card2 = D4;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFourPair();
            if(!result){
                System.out.println(result);
                ranker.hasFourPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFourPair_C1_False(){
        board = new Card[]{H8, D5, C7, S8, DK};
        card1 = D8;
        card2 = D4;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFourPair();
            if(!result){
                System.out.println(result);
                ranker.hasFourPair();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasFourPair_C2(){
        board = new Card[]{H8, D5, C8, S8, DK};
        card2 = D8;
        card1 = D4;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFourPair();
            if(!result){
                System.out.println(result);
                ranker.hasFourPair();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasStraight(){
        board = new Card[]{H8, D5, C6, SQ, DK};
        card1 = S4;
        card2 = S7;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraight();
            if(!result){
                System.out.println(result);
                ranker.hasStraight();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasStraight_False(){
        board = new Card[]{H9, D5, C6, SQ, DK};
        card1 = S4;
        card2 = S7;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraight();
            if(!result){
                System.out.println(result);
                ranker.hasStraight();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasStraight_AceHigh(){
        board = new Card[]{H8, D5, CA, SQ, DK};
        card1 = S10;
        card2 = SJ;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraight();
            if(!result){
                System.out.println(result);
                ranker.hasStraight();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasStraight_FiveHigh(){
        board = new Card[]{H3, D5, C7, S4, DK};
        card1 = S2;
        card2 = SA;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraight();
            if(!result){
                System.out.println(result);
                ranker.hasStraight();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasStraight_OneCard(){
        board = new Card[]{H8, D5, C6, S7, DK};
        card1 = S4;
        card2 = S10;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraight();
            if(!result){
                System.out.println(result);
                ranker.hasStraight();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFlush(){
        board = new Card[]{S8, S5, S6, S7, DK};
        card1 = S4;
        card2 = S10;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFlush();
            if(!result){
                System.out.println(result);
                ranker.hasFlush();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFlush_Board(){
        board = new Card[]{S8, S5, S6, S7, SK};
        card1 = H4;
        card2 = H10;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFlush();
            if(!result){
                System.out.println(result);
                ranker.hasFlush();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFlush_C1(){
        board = new Card[]{S8, S5, S6, S7, DK};
        card1 = S4;
        card2 = D10;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFlush();
            if(!result){
                System.out.println(result);
                ranker.hasFlush();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFlush_C2(){
        board = new Card[]{S8, S5, S6, S7, DK};
        card1 = D4;
        card2 = S10;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFlush();
            if(!result){
                System.out.println(result);
                ranker.hasFlush();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFlush_False(){
        board = new Card[]{S8, S5, S6, D7, DK};
        card1 = D4;
        card2 = S10;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFlush();
            if(!result){
                System.out.println(result);
                ranker.hasFlush();
            }
            assertFalse(result);
        }
    }

    @Test
    void hasFullHouse(){
        board = new Card[]{S8, D8, S6, D7, DK};
        card1 = S7;
        card2 = C8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFullHouse();
            if(!result){
                System.out.println(result);
                ranker.hasFullHouse();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFullHouse_False(){
        board = new Card[]{S8, D6, S6, D7, DK};
        card1 = S7;
        card2 = C8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFullHouse();
            if(!result){
                System.out.println(result);
                ranker.hasFullHouse();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasFullHouse_OneCard(){
        board = new Card[]{S8, D6, S6, D6, DK};
        card1 = S7;
        card2 = C8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFullHouse();
            if(!result){
                System.out.println(result);
                ranker.hasFullHouse();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFullHouse_OneCard_False(){
        board = new Card[]{S8, D6, S6, C6, DK};
        card1 = S7;
        card2 = C9;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFullHouse();
            if(result){
                System.out.println(result);
                ranker.hasFullHouse();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasFullHouse_PocketPair(){
        board = new Card[]{S8, D6, S6, C6, DK};
        card1 = S7;
        card2 = C7;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFullHouse();
            if(!result){
                System.out.println(result);
                ranker.hasFullHouse();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFullHouse_PocketPair_False(){
        board = new Card[]{S8, D6, S9, C6, DK};
        card1 = S7;
        card2 = C7;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFullHouse();
            if(!result){
                System.out.println(result);
                ranker.hasFullHouse();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasFullHouse_BoardPair(){
        board = new Card[]{S8, D6, S9, C6, DK};
        card1 = S6;
        card2 = C8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFullHouse();
            if(!result){
                System.out.println(result);
                ranker.hasFullHouse();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasFullHouse_Board2Pair(){
        board = new Card[]{S8, D6, D8, C6, DK};
        card1 = S10;
        card2 = C8;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasFullHouse();
            if(!result){
                System.out.println(result);
                ranker.hasFullHouse();
            }
            assertTrue(result);
        }
    }

    @Test
    void hasStraightFlush(){
        board = new Card[]{S8, S6, S7, C6, DK};
        card1 = S10;
        card2 = S9;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraightFlush();
            if(!result){
                System.out.println(result);
                ranker.hasStraightFlush();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasStraightFlush_False(){
        board = new Card[]{S8, S6, SJ, C6, DK};
        card1 = S10;
        card2 = S9;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraightFlush();
            if(!result){
                System.out.println(result);
                ranker.hasStraightFlush();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasStraightFlush_OneCard(){
        board = new Card[]{S8, S6, S10, S7, DK};
        card1 = S2;
        card2 = S9;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraightFlush();
            if(!result){
                System.out.println(result);
                ranker.hasStraightFlush();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasStraightFlush_OneCard_False(){
        board = new Card[]{S8, S6, S10, S7, DK};
        card1 = S2;
        card2 = SQ;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraightFlush();
            if(!result){
                System.out.println(result);
                ranker.hasStraightFlush();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasStraightFlush_TwoCard(){
        board = new Card[]{S8, S6, S10, C4, DK};
        card1 = S7;
        card2 = S9;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraightFlush();
            if(!result){
                System.out.println(result);
                ranker.hasStraightFlush();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasStraightFlush_WrongFlush(){
        board = new Card[]{S8, C6, S10, C4, SK};
        card1 = S7;
        card2 = S9;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraightFlush();
            if(!result){
                System.out.println(result);
                ranker.hasStraightFlush();
            }
            assertFalse(result);
        }
    }
    @Test
    void hasStraightFlush_5Board(){
        board = new Card[]{S8, S6, S5, S4, S7};
        card1 = C7;
        card2 = C9;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraightFlush();
            if(!result){
                System.out.println(result);
                ranker.hasStraightFlush();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasStraightFlush_5High(){
        board = new Card[]{S3, S5, S2, C4, DK};
        card1 = SA;
        card2 = S4;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.hasStraightFlush();
            if(!result){
                System.out.println(result);
                ranker.hasStraightFlush();
            }
            assertTrue(result);
        }
    }
    @Test
    void hasStraightFlush_AceHigh(){
        board = new Card[]{S10, SQ, SK, C4, DK};
        card1 = SA;
        card2 = SJ;
        getPermutations(board);
        boolean result = true;
        for(Card[] permutation : permutations){
            ranker = new HandRanker(permutation, card1, card2);
            result = result && ranker.rankHand().equals(HandRanker.HandRank.RoyalFlush);
            if(!result){
                System.out.println(result);
                ranker.hasStraightFlush();
            }
            assertTrue(result);
        }
    }
}