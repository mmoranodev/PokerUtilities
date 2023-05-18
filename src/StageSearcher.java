import java.util.ArrayList;
import java.util.Arrays;

public class StageSearcher {
    private Card[] board;
    private int[] cardValues;
    private Card.Suit[] cardSuits;
    private Card card1, card2;
    private Player player;
    private BoardSearcher boardSearcher;
    private int boardLength;
    private boolean aceHigh;
    public void setPlayer(Player player){
        this.player = player;
        card1 = player.getCard1();
        card2 = player.getCard2();
        if(board[3] == null)
            boardLength = 3;
        else if(board[4] == null)
            boardLength = 4;
        else
            boardLength = 5;
    }

    public StageSearcher(Card[] board, Player player){
        this.board = board;
        setPlayer(player);
        cardSuits = new Card.Suit[boardLength + 2];
        cardValues = new int[boardLength + 2];
        for(int i = 0; i < boardLength; i++){
            cardSuits[i] = board[i].getSuit();
            cardValues[i] = board[i].getValue();
        }
        cardValues[boardLength] = card1.getValue();
        cardValues[boardLength + 1] = card2.getValue();
        cardSuits[boardLength] = card1.getSuit();
        cardSuits[boardLength + 1] = card2.getSuit();
        boardSearcher = new BoardSearcher(board);
    }
    public boolean hasPair(int num){
        int count1 = 1, count2 = 1;
        if(hasPocketPair()){
            if(num == 2)
                return true;
            else
                count1++;
        }
        if(boardHasPair(num))
            return true;
        for(int i = 0; i < boardLength; i++) {
            if (cardValues[i] == card1.getValue())
                count1++;
            else if(cardValues[i] == card2.getValue())
                count2++;
            if(count1 == num || count2 == num)
                return true;
        }
        return false;
    }
    public boolean hasTwoPair(){
        int count1 = 1, count2 = 1;
        boolean boardPairPresent = boardSearcher.pairPresent();
        if((hasPocketPair() && boardPairPresent) || boardSearcher.twoPairPresent())
            return true;
        for(int i = 0; i < boardLength; i++) {
            if (cardValues[i] == card1.getValue())
                count1++;
            else if(cardValues[i] == card2.getValue())
                count2++;
            if(count1 == 2 && count2 == 2)
                return true;
            else if(boardPairPresent && (count1 == 2 || count2 == 2))
                return true;
        }
        return false;
    }

    public boolean hasStraight(){
        if(board[4] != null && boardSearcher.straightPresent())
            return true;
        Arrays.sort(cardValues);
        for(int i = boardLength - 1; i >= 0; i--){

        }
        return false;
    }
    //hasStraight
    //hasFlush
    //hasFullHouse
    //hasRoyalFlush?
    //isSequence



    private boolean isSequence(int[] values){
        Arrays.sort(values);
        for(int i = 0; i < values.length - 1; i++){
            if(values[i + 1] != values[i] + 1){
                if(i == 3 && values[0] == 1 && values[4] == 13)
                    continue;
                return false;
            }
        }
        if(values[4] == 13 && values[3] == 12)
            aceHigh = true;
        return true;
    }
    private boolean hasPocketPair(){
        return card1.getValue() == card2.getValue();
    }
    private boolean boardHasPair(int num){
        switch (num){
            case 2:
                if(boardSearcher.pairPresent())
                    return true;
                break;
            case 3:
                if(boardSearcher.threePairPresent())
                    return true;
                break;
            case 4:
                if(boardSearcher.fourPairPresent())
                    return true;
                break;
        }
        return false;
    }
}
