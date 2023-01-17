import java.util.Arrays;

public class BoardSearcher {
    private Card[] board;
    public BoardSearcher(Card[] board){
        this.board = board;
    }

    public boolean pairPresent(){
        Card checkCard;
        int cardPos;
        for(int i = 0; i < 5; i++){
            checkCard = board[i];
            for(int k = 0; k < 5; k++){
                if(!checkCard.equals(board[k]) && checkCard.getValue() == board[k].getValue())
                    return true;
            }
        }
        return false;
    }
    public boolean twoPairPresent() {
        Card checkCard;
        Card.Rank firstPair = null;
        int count = 1, pairCount = 0;
        for (int i = 1; i < 5; i++){
            for(int k = 1; k < 5; k++){
                checkCard = board[count - 1];
                if(!checkCard.equals(board[k]) && checkCard.getValue() == board[k].getValue() &&
                        board[k].getRank() != firstPair) {
                    pairCount++;
                    firstPair = board[k].getRank();
                }
                if(pairCount == 2)
                    return true;
            }
            count++;
        }
        return false;
    }
    public boolean threePairPresent(){
        Card checkCard = board[0];
        int cardCount = 1;
        for(int i = 0; i < 5; i++){
            for(int k = 0; k < 5; k++){
                if(!checkCard.equals(board[i]) &&
                        checkCard.getValue() == board[k].getValue())
                    cardCount++;
                if(cardCount == 3)
                    return true;
            }
            cardCount = 0;
            checkCard = board[i];
        }
        return false;
    }
    public boolean fourPairPresent(){
        Card checkCard = board[0];
        int cardCount = 1;
        for(int i = 0; i < 5; i++){
            for(int k = 0; k < 5; k++){
                if(!checkCard.equals(board[i]) &&
                        checkCard.getValue() == board[k].getValue())
                    cardCount++;
                if(cardCount == 4)
                    return true;
            }
            cardCount = 0;
            checkCard = board[i];
        }
        return false;
    }
    public boolean straightPresent()
    {
        int[] values = new int[5];
        for(int i = 0; i < 5; i++){
            values[i] = board[i].getValue();
        }
        return isSequence(values);
    }

    private boolean isSequence(int[] values){
        Arrays.sort(values);
        for(int i = 0; i < 4; i++){
            if(values[i + 1] != values[i] + 1){
                if(i == 3 && values[0] == 1 && values[4] == 13)
                    continue;
                return false;
            }
        }
        return true;
    }
}
