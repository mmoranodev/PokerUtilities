import java.util.ArrayList;
import java.util.Arrays;

public class StageSearcher {
    protected Card[] board;
    protected int[] cardValues;
    protected Card.Suit[] cardSuits;
    protected Card card1, card2;
    protected Player player;
    private BoardSearcher boardSearcher;
    private boolean aceHigh;
    public void setPlayer(Player player){
        this.player = player;
        card1 = player.getCard1();
        card2 = player.getCard2();
    }

    public StageSearcher(Card[] board, Player player){
        this.board = board;
        this.card1 = player.getCard1();
        this.card2 = player.getCard2();
        cardSuits = new Card.Suit[board.length];
        cardValues = new int[board.length];
        for(int i = 0; i < board.length; i++){
            cardSuits[i] = board[i].getSuit();
            cardValues[i] = board[i].getValue();
        }
        boardSearcher = new BoardSearcher(board);
    }
    protected boolean hasPair(int num){
        int count1 = 1, count2 = 1;
        if(hasPocketPair()){
            if(num == 2)
                return true;
            else
                count1++;
        }
        if(boardHasPair(num))
            return true;
        for(int i = 0; i < board.length; i++) {
            if (cardValues[i] == card1.getValue())
                count1++;
            else if(cardValues[i] == card2.getValue())
                count2++;
            if(count1 == num || count2 == num)
                return true;
        }
        return false;
    }
    protected boolean hasTwoPair(){
        int count1 = 1, count2 = 1;
        boolean boardPairPresent = boardSearcher.pairPresent();
        if((hasPocketPair() && boardPairPresent) || boardSearcher.twoPairPresent())
            return true;
        for(int i = 0; i < board.length; i++) {
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

    protected boolean hasStraight(){
        Arrays.sort(cardValues);
        int low = cardValues[0], high = cardValues[0], count = 1;
        for(int i = 0; i < cardValues.length - 1; i++){

        }
        return false;
    }


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
    //hasStraight
    //hasFlush
    //hasFullHouse
    //hasRoyalFlush?
    //isSequence

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
