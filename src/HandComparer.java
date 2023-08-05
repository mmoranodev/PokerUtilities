import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class HandComparer {
    private Card[] board;
    private int pot;
    private ArrayList<Player> players;
    public HandComparer(Table table){
        board = table.getBoard();
        pot = table.getPot();
        players = table.getPlayers();
    }
    public HandComparer(Card[] board, SidePot sidePot){
        this.board = board;
        pot = sidePot.getPot();
        players = sidePot.getPlayers();
    }

    /**
     * Finds winning player(s) and updates winner(s) money.
     * @return Arraylist of winning player(s)
     */
    public ArrayList<Player> findWinner(){
        ArrayList<Player> tiedPlayers = new ArrayList<>();
        ArrayList<Player> eligiblePlayers = new ArrayList<>((players.stream().filter(x -> !x.hasFolded() && !x.isBankrupt()).collect(Collectors.toList())));

        Player winner = eligiblePlayers.get(0);
        tiedPlayers.add(winner);
        //find player(s) with highest hand rank
        for(int i = 1; i < eligiblePlayers.size(); i++){
            int compare = winner.getHandRank().value - eligiblePlayers.get(i).getHandRank().value;
            //new winner
            if(compare < 0){
                winner = eligiblePlayers.get(i);
                tiedPlayers.clear();
                tiedPlayers.add(winner);
            }else if(compare == 0){//Tie
                tiedPlayers.add(eligiblePlayers.get(i));
            }
        }
        //find winner(s) from players with same hand rank and update player money
        if(tiedPlayers.size() > 1){
            ArrayList<Player> winners = new ArrayList<>();
            winner = tiedPlayers.get(0);
            winners.add(winner);
            for(int i = 1; i < tiedPlayers.size(); i++){
                switch (compareHand(winner, tiedPlayers.get(i))){
                    case 0://tie
                        winners.add(tiedPlayers.get(i));
                        break;
                    case 1://current winner
                        break;
                    case 2://new winner
                        winner = tiedPlayers.get(i);
                        winners.clear();
                        winners.add(winner);
                        break;
                }
            }
            if(winners.size() > 1)
                payWinner(pot, winners);
            else
                payWinner(pot, winner);
            return winners;
        }
        payWinner(pot, winner);
        return new ArrayList<>(List.of(winner));
    }
    private void payWinner(int pot, ArrayList<Player> winners){
        int splitPot = pot / winners.size();
        for(Player player : winners){
            payWinner(splitPot, player);
        }
    }
    private void payWinner(int pot, Player winner){
        winner.setMoney(winner.getMoney() + pot);
    }
    private int compareHand(Player p1, Player p2){
        HandRanker.HandRank rank = p1.getHandRank();
        int winner;
        switch (rank){
            case StraightFlush:
            case Straight:
                winner = compareStraight(p1, p2);
                break;
            case FourPair:
                winner = compareFourPair(p1, p2);
                break;
            case FullHouse:
                winner = compareFullHouse(p1, p2);
                break;
            case Flush:
                winner = compareFlush(p1, p2);
                break;
            case ThreePair:
                winner = compareThreePair(p1, p2);
                break;
            case TwoPair:
                winner = compareTwoPair(p1, p2);
                break;
            case Pair:
                winner = comparePair(p1, p2);
                break;
            default:
                winner = compareHighCard(p1, p2);
        }
        return winner;
    }

    private int compareHighCard(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.HighCard);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.HighCard);
        return compareValues(p1Cards, p2Cards, 0);
    }
    private int compareStraight(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.Straight);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.Straight);
        return compareTopCard(p1Cards[4], p2Cards[4]);
    }
    private int compareFourPair(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.FourPair);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.FourPair);
        return compareTopCard(p1Cards[4], p2Cards[4]);
    }
    private int compareThreePair(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.ThreePair);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.ThreePair);
        return compareValues(p1Cards, p2Cards, 3);
    }
    private int comparePair(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.Pair);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.Pair);
        return compareValues(p1Cards, p2Cards, 2);
    }
    private int compareTwoPair(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.TwoPair);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.TwoPair);
        return compareValues(p1Cards, p2Cards, 0);
    }
    private int compareFlush(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.Flush);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.Flush);
        return compareValues(p1Cards, p2Cards, 0);
    }
    private int compareFullHouse(Player p1, Player p2){
        int[] p1Cards = getTopCards(p1.getCard1(), p1.getCard2(), HandRanker.HandRank.FullHouse);
        int[] p2Cards = getTopCards(p2.getCard1(), p2.getCard2(), HandRanker.HandRank.FullHouse);
        int threeCardWinner = compareTopCard(p1Cards[0], p2Cards[0]);
        if(threeCardWinner == 0)
            return compareTopCard(p1Cards[3], p2Cards[3]);
        return threeCardWinner;
    }

    private int compareValues(int[] p1Cards, int[] p2Cards, int searchToValue){
        for(int i = 4; i >= searchToValue; i--) {
            if (p1Cards[i] > p2Cards[i])
                return 1;
            else if (p1Cards[i] < p2Cards[i])
                return 2;
        }
        return 0;
    }
    private int compareTopCard(int p1, int p2){
        if(p1 > p2)
            return 1;
        else if(p1 < p2)
            return 2;
        else
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
                reverseSort(cardValues);
                int threePairValue = findPairedValue(cardValues, 3);
                cardValues = Arrays.stream(cardValues).filter(x -> x != threePairValue).toArray();
                pairValue = findPairedValue(cardValues, 2);
                result = new int[]{threePairValue, threePairValue, threePairValue, pairValue, pairValue};
                break;
            case Flush:
                Card[] allCards = new Card[7];
                for(int i = 0; i < 5; i++)
                    allCards[i] = board[i];
                allCards[5] = c1;
                allCards[6] = c2;
                ArrayList<Integer> flushValues = getFlushCards(allCards);
                result = Arrays.copyOfRange(flushValues.stream().mapToInt(x -> x).toArray(), flushValues.size() - 5, flushValues.size());
                Arrays.sort(result);
                break;
            case ThreePair:
                pairValue = findPairedValue(cardValues, 3);
                cardValues = Arrays.stream(cardValues).filter(x -> x != pairValue).toArray();
                result = new int[] {pairValue, pairValue, pairValue, cardValues[cardValues.length - 2], cardValues[cardValues.length - 1]};
                break;
            case TwoPair:
                reverseSort(cardValues);
                pairValue = findPairedValue(cardValues, 2);
                cardValues = Arrays.stream(cardValues).filter(x -> x != pairValue).toArray();
                int secondPairValue = findPairedValue(cardValues, 2);
                cardValues = Arrays.stream(cardValues).filter(x -> x != secondPairValue).toArray();
                result = new int[] {pairValue, pairValue, secondPairValue, secondPairValue, cardValues[0]};
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
    //get the highest sequence of cards
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
    //get the all card values of flush
    private ArrayList<Integer> getFlushCards(Card[] allCards){
        ArrayList<Integer> result = new ArrayList<>();
        HashMap<Card.Suit, ArrayList<Integer>> suitCounts = new HashMap<>(Map.of(
                Card.Suit.Diamond, new ArrayList<>(),
                Card.Suit.Club, new ArrayList<>(),
                Card.Suit.Spade, new ArrayList<>(),
                Card.Suit.Heart, new ArrayList<>()
        ));
        for(Card card : allCards){
            ArrayList<Integer> currentList = suitCounts.get(card.getSuit());
            currentList.add(card.getValue());
            if(currentList.size() > result.size())
                result = currentList;
        }
        Collections.sort(result);
        return result;
    }
    private void reverseSort(int[] arr){
        Arrays.sort(arr);
        int temp;
        for(int i = 0; i < arr.length / 2; i++){
            temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
    }
}
