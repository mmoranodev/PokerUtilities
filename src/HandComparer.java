import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HandComparer {
    private Card[] board;
    private int pot;
    private ArrayList<Player> players;
    private ArrayList<SidePot> sidePots;
    public HandComparer(Table table){
        board = table.getBoard();
        pot = table.getPot();
        players = table.getPlayers();
        sidePots = table.getSidePots();
    }
    /**
     finds player with the highest hand strength
     *compares hands with equal hand strength
     *update player money
     * @return
     */
    public void findWinner(){

    }
    /**
     * compare hands of equal rank
     * return 1, 2, or 0 if tie
     * @param p1 player 1
     * @param p2 player 2
     * @param rank hand rank
     * @return
     */
    public int compareHand(Player p1, Player p2, Card.Rank rank){
        int winner = 0;
        return winner;
    }

    public int compareHighCard(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.HighCard);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.HighCard);
        for(int i = 4; i >= 0; i--){
            if(p1Cards[i] > p2Cards[i])
                return 1;
            else if(p1Cards[i] < p2Cards[i])
                return 2;
        }
        return 0;
    }
    public int compareStraight(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.Straight);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.Straight);
        if(p1Cards[4] > p2Cards[4])
            return 1;
        else if(p1Cards[4] < p2Cards[4])
            return 2;
        else
            return 0;
    }
    public int compareFourPair(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.FourPair);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.FourPair);
        if(p1Cards[4] > p2Cards[4])
            return 1;
        else if(p1Cards[4] < p2Cards[4])
            return 2;
        else
            return 0;
    }
    public int compareThreePair(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.ThreePair);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.ThreePair);
        for(int i = 4; i >= 3; i--) {
            if (p1Cards[i] > p2Cards[i])
                return 1;
            else if (p1Cards[i] < p2Cards[i])
                return 2;
        }
        return 0;
    }
    public int comparePair(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.Pair);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.Pair);
        for(int i = 4; i >= 2; i--) {
            if (p1Cards[i] > p2Cards[i])
                return 1;
            else if (p1Cards[i] < p2Cards[i])
                return 2;
        }
        return 0;
    }

    //Gets best 5 cards based upon hand rank
    private int[] getTopCards(Card c1, Card c2, HandRanker.HandRank rank){
        int[] result = null, cardValues = new int[7];
        int pairValue;
        for(int i = 0; i < 5; i++)
            cardValues[i] = board[i].getValue();
        cardValues[5] = c1.getValue();
        cardValues[6] = c2.getValue();
        Arrays.sort(cardValues);
        switch (rank){
            case StraightFlush:
            case Straight:
                //get straight values
                ArrayList<Integer> straightValues = getStraightCards(cardValues);
                straightValues = straightValues.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
                //if 5 high straight, move ace to front of list for compare as is the lowest card
                if(straightValues.contains(Card.Rank.Ace.value) && straightValues.contains(Card.Rank.Two.value)){
                    int temp;
                    for(int i = straightValues.size() - 1; i > 0; i--){
                        temp = straightValues.get(i);
                        straightValues.set(i, straightValues.get(i - 1));
                        straightValues.set(i - 1, temp);
                    }
                }//return highest 5 cards in straight
                result = Arrays.copyOfRange(straightValues.stream().mapToInt(x -> x).toArray(), straightValues.size() - 5, straightValues.size());
                break;
            case FourPair:
                pairValue = findPairedValue(cardValues, 4);
                cardValues = Arrays.stream(cardValues).filter(x -> x != pairValue).toArray();
                result = new int[] {pairValue, pairValue, pairValue, pairValue, cardValues[cardValues.length - 1]};
                break;
            case FullHouse:

                break;
            case Flush:

                break;
            case ThreePair:
                pairValue = findPairedValue(cardValues, 3);
                cardValues = Arrays.stream(cardValues).filter(x -> x != pairValue).toArray();
                result = new int[] {pairValue, pairValue, pairValue, cardValues[cardValues.length - 2], cardValues[cardValues.length - 1]};
                break;
            case TwoPair:

                break;
            case Pair:
                pairValue = findPairedValue(cardValues, 2);
                cardValues = Arrays.stream(cardValues).filter(x -> x != pairValue).toArray();
                result = new int[] {pairValue, pairValue, cardValues[cardValues.length - 3], cardValues[cardValues.length - 2], cardValues[cardValues.length - 1]};
                break;
            default: 
                result = Arrays.copyOfRange(cardValues, 2, 7);
        }
        return result;
    }
    //finds paired value in array of card values
    //num param 2, 3, 4 based on pair, three/four of kind
    private int findPairedValue(int[] values, int num){
        int result = 0, count = 1;
        for(int i = 0; i < values.length; i++){
            for(int k = 0; k < values.length; k++){
                if(i != k && values[i] == values[k])
                    count++;
                if(count == num) {
                    result = values[i];
                    break;
                }
            }
            if(result > 0)
                break;
            count = 1;
        }
        return result;
    }

    private ArrayList<Integer> getStraightCards(int[] allCards){
        ArrayList<Integer> result = new ArrayList<>();
        int low = allCards[0], high = low, pos = 0;
        int currentValue = allCards[0];
        int currentCard;
        for(int card : allCards){
            currentCard = allCards[pos];
            if(currentValue == currentCard - 1 || currentValue == currentCard){
                result.add(card);
                currentValue = currentCard;
                high = currentValue;
            }
            else if(low == Card.Rank.Two.value && high == Card.Rank.Five.value && allCards[6] == Card.Rank.Ace.value)
                result.add(allCards[6]);
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
        return result;
    }

    private boolean hasPocketPair(Player player){
        return player.getCard1().getValue() == player.getCard2().getValue();
    }
}
