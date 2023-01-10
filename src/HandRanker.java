public class HandRanker {
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
}
