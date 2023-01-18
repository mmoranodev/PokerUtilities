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
        if(card1.getValue() == card2.getValue())
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
        if(card1.getValue() == card2.getValue()) {
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
}
