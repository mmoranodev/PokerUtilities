import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StageSearcher {
    private Card[] board;
    private int[] cardValues;
    private ArrayList<Card> allCards;
    private Card.Suit[] cardSuits;
    private Card card1, card2;
    private Player player;
    private BoardSearcher boardSearcher;
    private HandRanker handRanker;
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
        allCards = new ArrayList<>();
        for(int i = 0; i < boardLength; i++){
            cardSuits[i] = board[i].getSuit();
            cardValues[i] = board[i].getValue();
            allCards.add(board[i]);
        }
        cardValues[boardLength] = card1.getValue();
        cardValues[boardLength + 1] = card2.getValue();
        cardSuits[boardLength] = card1.getSuit();
        cardSuits[boardLength + 1] = card2.getSuit();
        allCards.add(card1);
        allCards.add(card2);
        boardSearcher = new BoardSearcher(board);
        handRanker = new HandRanker();
    }
    public HandRanker.HandRank getCurrentHandRank(){
        if(hasStraightFlush())
            return aceHigh ? HandRanker.HandRank.RoyalFlush : HandRanker.HandRank.StraightFlush;
        else if(hasPair(4))
            return HandRanker.HandRank.FourPair;
        else if(hasFullHouse())
            return HandRanker.HandRank.FullHouse;
        else if(hasFlush())
            return HandRanker.HandRank.Flush;
        else if(hasStraight())
            return HandRanker.HandRank.Straight;
        else if(hasPair(3))
            return HandRanker.HandRank.ThreePair;
        else if(hasTwoPair())
            return HandRanker.HandRank.TwoPair;
        else if(hasPair(2))
            return HandRanker.HandRank.Pair;
        else
            return HandRanker.HandRank.HighCard;
    }
    private boolean hasPair(int num){
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
    private boolean hasTwoPair(){
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

    private boolean hasStraight(){
        if(board[4] != null && boardSearcher.straightPresent())
            return true;
        Arrays.sort(cardValues);
        ArrayList<Integer> result = new ArrayList<>();
        int low = cardValues[0], high = low, pos = 0;
        int currentValue = cardValues[0];
        int currentCard;
        for(int card : cardValues){
            currentCard = cardValues[pos];
            if(currentValue == currentCard - 1 || currentValue == currentCard){
                result.add(card);
                currentValue = currentCard;
                high = currentValue;
            }
            else if(low == Card.Rank.Two.value && high == Card.Rank.Five.value && cardValues[cardValues.length - 1] == Card.Rank.Ace.value)
                result.add(cardValues[cardValues.length - 1]);
            else if(result.size() >=5)
                continue;
            else{
                result.clear();
                result.add(currentCard);
                currentValue = currentCard;
                low = currentValue;
                high = currentValue;
            }
            pos++;
        }
        return result.size() >= 5;
    }
    private boolean hasFlush(){
        int count = 0;
        Card.Suit currentSuit;
        for(int i = 0; i < cardSuits.length; i++){
            currentSuit = cardSuits[i];
            for(int k = 0; k < cardSuits.length; k++){
                if(currentSuit == cardSuits[k])
                    count++;
            }
            if(count >= 5)
                return true;
            count = 0;
        }
        return false;
    }

    private boolean hasFullHouse(){
        if(boardLength == 5 && boardSearcher.fullHousePresent())
            return true;
        if(hasPocketPair()) {
            if (boardSearcher.threePairPresent())
                return true;
                //if pocket pair and board pair present, return true if three of a kind with pocket pair
            else if(boardSearcher.pairPresent()){
                for(int i = 0; i < boardLength; i++)
                    if(card1.getValue() == board[i].getValue())
                        return true;
            }
        }//if three of kind present in board, find pair from hand
        else if(boardSearcher.threePairPresent()){
            for(int i = 0; i < boardLength; i++){
                if(card1.getValue() == board[i].getValue())
                    return true;
                if(card2.getValue() == board[i].getValue())
                    return true;
            }
        }
        else if(boardSearcher.twoPairPresent()){
            for(int i = 0; i < boardLength; i++) {
                if (card1.getValue() == board[i].getValue())
                    return true;
                if(card2.getValue() == board[i].getValue())
                    return true;
            }
        }
        else{
            int count1 = 1, count2 = 1;
            for(int i = 0; i < boardLength; i++){
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
    private boolean hasStraightFlush(){
        Collections.sort(allCards, (card1, card2) -> Integer.compare(card1.getValue(), card2.getValue()));
        ArrayList<Card> straightCards = handRanker.getStraightCards(allCards);
        if(straightCards.size() >= 5) {
            aceHigh = straightCards.get(straightCards.size() - 1).getRank() == Card.Rank.Ace;
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
                                    aceHigh = true;
                        return true;
                    }
                }
                if(count == 5)//straight flush found
                {//determine if royal flush
                    if(cards.get(cards.size() - 1).getRank() == Card.Rank.Ace)
                        for(int j = cards.size() - 1; j < 0; j--)
                            if(cards.get(j).getRank() == Card.Rank.King)
                                aceHigh = true;
                    return true;
                }
            }//straight flush not found
            return false;
        }
        return true;
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
