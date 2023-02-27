import java.util.*;

public class HandRanker {
    private Card[] board;
    private Card card1, card2;
    private BoardSearcher boardSearcher;
    private boolean isAceHigh;
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

    public HandRank rankHand()
    {
        if(hasStraightFlush())
            return isAceHigh ? HandRank.RoyalFlush : HandRank.StraightFlush;
        if(hasFourPair())
            return HandRank.FourPair;
        if(hasFullHouse())
            return HandRank.FullHouse;
        if(hasFlush())
            return HandRank.Flush;
        if(hasStraight())
            return HandRank.Straight;
        if(hasThreePair())
            return HandRank.ThreePair;
        if(hasTwoPair())
            return HandRank.TwoPair;
        if(hasPair())
            return HandRank.Pair;
        return HandRank.HighCard;
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
            //if pocket pair and board pair present, return true if three of a kind with pocket pair
            else if(boardSearcher.pairPresent()){
                for(int i = 0; i < 5; i++)
                    if(card1.getValue() == board[i].getValue())
                        return true;
            }
        }//if three of kind present in board, find pair from hand
        else if(boardSearcher.threePairPresent()){
            for(int i = 0; i < 5; i++){
                if(card1.getValue() == board[i].getValue())
                    return true;
                if(card2.getValue() == board[i].getValue())
                    return true;
            }
        }
        else if(boardSearcher.twoPairPresent()){
            for(int i = 0; i < 5; i++) {
                if (card1.getValue() == board[i].getValue())
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
    public boolean hasStraightFlush(){
        if(boardSearcher.straightFlushPresent())
            return true;
        ArrayList<Card> allCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            allCards.add(board[i]);
        }
        allCards.add(card1);
        allCards.add(card2);
        Collections.sort(allCards, (c1, c2) -> {return Integer.compare(c1.getValue(), c2.getValue());});
        ArrayList<Card> straightCards = getStraightCards(allCards);
        if(straightCards.size() >= 5) {
            return hasCorrectFlush(straightCards);
        }
        return false;
    }

    private boolean hasCorrectFlush(ArrayList<Card> cards){
        if(cards.size() == 5){
            Card.Suit suit = cards.get(0).getSuit();
            for(Card card : cards)
                if(!card.getSuit().equals(suit))
                    return false;
        }
        else{
            int count, aceCount, currentValue = cards.get(0).getValue();
            Card.Suit suit;
            Card card;
            //go through the list to see if there is a straight flush
            for(int i = 0; i < cards.size(); i++){
                count = 1;
                aceCount = 0;
                suit = cards.get(i).getSuit();
                for(int k = 0; k < cards.size(); k++){
                    card = cards.get(k);
                    if(card.getRank() == Card.Rank.Ace)
                        aceCount++;
                    //check if comparing same card
                    if(!cards.get(i).equals(cards.get(k))){
                        //if card is the same suit and sequential, count++
                        if(card.getSuit() == suit && currentValue == card.getValue() - 1) {
                            count++;
                            currentValue = card.getValue();
                        }
                        else if(card.getRank() == Card.Rank.Ace && cards.get(k - aceCount).getRank() == Card.Rank.Five && card.getSuit() == suit)
                            count++;
                        //if cards have same value continue
                        else if(currentValue == card.getValue())
                            continue;
                        //if current card and next card have same value but first is incorrect suit, check next card
                        else if(k < cards.size() - 1 && currentValue + 1 == cards.get(k + 1).getValue())
                            continue;
                        else//next card is not same suit, break and check next
                            break;
                    }
                    if(count == 5)//straight flush found
                    {//determine if royal flush
                        if(cards.get(cards.size() - 1).getRank() == Card.Rank.Ace)
                            for(int j = cards.size() - 1; j > 0; j--)
                                if(cards.get(j).getRank() == Card.Rank.King)
                                    isAceHigh = true;
                        return true;
                    }
                }
                if(count == 5)//straight flush found
                {//determine if royal flush
                    if(cards.get(cards.size() - 1).getRank() == Card.Rank.Ace)
                        for(int j = cards.size() - 1; j < 0; j--)
                            if(cards.get(j).getRank() == Card.Rank.King)
                                isAceHigh = true;
                    return true;
                }
            }//straight flush not found
            return false;
        }
        return true;
    }
    private ArrayList<Card> getStraightCards(ArrayList<Card> allCards){
        ArrayList<Card> result = new ArrayList<>();
        int low = allCards.get(0).getValue(), high = low, pos = 0;
        int currentValue = allCards.get(0).getValue();
        Card currentCard;
        for(Card card : allCards){
            currentCard = allCards.get(pos);
            if(currentValue == currentCard.getValue() - 1 || currentValue == currentCard.getValue()){
                result.add(card);
                currentValue = currentCard.getValue();
                high = currentValue;
            }
            else if(low == Card.Rank.Two.value && high == Card.Rank.Five.value && allCards.get(6).getRank() == Card.Rank.Ace)
                result.add(allCards.get(6));
            else if(result.size() >=5)
                continue;
            else{
                result.clear();
                result.add(currentCard);
                currentValue = currentCard.getValue();
                low = currentValue;
                high = currentValue;
            }
            pos++;
        }
        return result;
    }

    private boolean hasPocketPair(){
        return card1.getValue() == card2.getValue();
    }
}
