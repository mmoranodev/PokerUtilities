public class BoardSearcher {
    private Card[] board;
    public BoardSearcher(Card[] board){
        this.board = board;
    }

//    public boolean pairPresent(){
//        Card checkCard;
//        int count = 1;
//        for(int i = 1; i < 5; i++){
//            for(int k = 1; k < 5; k++){
//                checkCard = board[count - 1];
//                if(checkCard.getValue() == board[k].getValue())
//                    return true;
//            }
//            count++;
//        }
//        return false;
//    }
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
//    public boolean twoPairPresent() {
//        Card checkCard;
//        Card.Rank firstPair = null;
//        int count = 1, pairCount = 0;
//        for (int i = 1; i < 5; i++){
//            for(int k = 1; k < 5; k++){
//                checkCard = board[count - 1];
//                if(checkCard.getValue() == board[k].getValue() &&
//                board[k].getRank() != firstPair) {
//                    pairCount++;
//                    firstPair = board[k].getRank();
//                }
//                checkCard = board[k];
//            }
//            count++;
//        }
//        return pairCount == 2;
//    }
}
