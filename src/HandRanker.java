import java.util.Arrays;

public class HandRanker {
    private Card[] board;
    private Card card1, card2;
    private BoardSearcher boardSearcher;
    public HandRanker(Card[] board, Card card1, Card card2){
        this.board = board;
        this.card1 = card1;
        this.card2 = card2;
        boardSearcher = new BoardSearcher(board);
    }
    public enum HandRank {
        HighCard(0),
        Pair(1),
        TwoPair(2),
        ThreePair(3),
        Straight(4),
        Flush(5),
        FullHouse(6),
        FourPair(7),
        StraightFlush(8),
        RoyalFlush(9);
        public final int value;
        private HandRank(int value) {
            this.value = value;
        }
    }

    public HandRank rankHand(Card c1, Card c2, Card[] board)
    {
        return HandRank.Pair;
    }

    public boolean hasPair(){
        if(hasPocketPair())
            return true;
        if(boardSearcher.pairPresent())
            return true;
        for(int i = 0; i < 5; i++){
            if(card1.getValue() == board[i].getValue() || card2.getValue() == board[i].getValue())
                return true;
        }
        return false;
    }
    public boolean hasTwoPair(){
        if(boardSearcher.twoPairPresent())
            return true;
        Card firstPair = boardSearcher.getBoardPair();
        if(hasPocketPair()) {
            if(firstPair != null && firstPair.getValue() != card1.getValue())
                return true;
            if(firstPair == null)
                firstPair = card1;
        }
        for(int i = 0; i < 5; i++) {
            if(card1.getValue() == board[i].getValue()){
                if(firstPair != null && firstPair.getValue() != card1.getValue())
                    return true;
                else
                    firstPair = card1;
            }
            else if(card2.getValue() == board[i].getValue()){
                if(firstPair != null && firstPair.getValue() != card2.getValue())
                    return true;
                else
                    firstPair = card2;
            }
        }
        return false;
    }
    public boolean hasThreePair(){
        if(boardSearcher.threePairPresent())
            return true;
        if(hasPocketPair())
        {
            for(int i = 0; i < 5; i++)
                if(board[i].getValue() == card1.getValue())
                    return true;
        }
        int card1Count = 1, card2Count = 1;
        for(int i = 0; i < 5; i++){
            if(card1.getValue() == board[i].getValue())
                card1Count++;
            else if(card2.getValue() == board[i].getValue())
                card2Count++;
        }
        return card1Count == 3 || card2Count == 3;
    }
    public boolean hasFourPair(){
        if(boardSearcher.fourPairPresent())
            return true;
        if(hasPocketPair())
        {
            int count = 2;
            for(int i = 0; i < 5; i++)
                if(board[i].getValue() == card1.getValue())
                    count++;
            if(count == 4)
                return true;
        }
        int card1Count = 1, card2Count = 1;
        for(int i = 0; i < 5; i++){
            if(card1.getValue() == board[i].getValue())
                card1Count++;
            else if(card2.getValue() == board[i].getValue())
                card2Count++;
        }
        return card1Count == 4 || card2Count == 4;
    }
    public boolean hasStraight() {
        if (boardSearcher.straightPresent())
            return true;
        //sort board values
        int[] boardValues = new int[7];
        for (int i = 0; i < 5; i++) {
            boardValues[i] = board[i].getValue();
        }
        boardValues[5] = card1.getValue();
        boardValues[6] = card2.getValue();
        Arrays.sort(boardValues);
        int count = 1, low = boardValues[0], high = low;
        int currentValue = boardValues[0];
        for (int i = 1; i < 7; i++){
            //next card is sequential
            if(currentValue == boardValues[i] - 1){
                count++;
                currentValue = boardValues[i];
                high = currentValue;
            }
            //do nothing if next card is same value
            else if(currentValue == boardValues[i]) {}
            //determine if is 5 high straight
            else if(low == Card.Rank.Two.value && high == Card.Rank.Five.value && boardValues[6] == Card.Rank.Ace.value)
                return true;
            //restart count if not sequential
            else{
                count = 1;
                currentValue = boardValues[i];
                low = currentValue;
                high = currentValue;
            }
            if(count == 5)
                return true;
        }
        return false;
    }
    public boolean hasFlush(){
        if(boardSearcher.flushPresent())
            return true;
        if(card1.getSuit().equals(card2.getSuit())) {
            int count;
            Card.Suit suit = card1.getSuit();
            count = 2;
            for (int i = 0; i < 5; i++) {
                if (board[i].getSuit().equals(suit))
                    count++;
                if (count == 5)
                    return true;
            }
        }
        else {
            int count1 = 1, count2 = 1;
            Card.Suit suit1 = card1.getSuit(), suit2 = card2.getSuit();
            for (int i = 0; i < 5; i++) {
                if (board[i].getSuit().equals(suit1))
                    count1++;
                if (board[i].getSuit().equals(suit2))
                    count2++;
                if (count1 == 5 || count2 == 5)
                    return true;
            }
        }
        return false;
    }
    public boolean hasFullHouse(){
        if(boardSearcher.fullHousePresent())
            return true;
        if(hasPocketPair()) {
            if (boardSearcher.threePairPresent())
                return true;
        }
        else if(boardSearcher.threePairPresent()){
            for(int i = 0; i < 5; i++){
                if(card1.getValue() == board[i].getValue())
                    return true;
                if(card2.getValue() == board[i].getValue())
                    return true;
            }
        }
        else{
            int count1 = 1, count2 = 1;
            for(int i = 0; i < 5; i++){
                if(card1.getValue() == board[i].getValue())
                    count1++;
                else if(card2.getValue() == board[i].getValue())
                    count2++;
                if((count1 == 2 && count2 == 3) || (count1 == 3 && count2 == 2))
                    return true;
            }
        }
        return false;
    }

    private boolean hasPocketPair(){
        return card1.getValue() == card2.getValue();
    }
}
