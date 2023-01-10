public class Card {
    private Rank rank;
    private Suit suit;
    private int value;
    public Card(){}
    public Card(Rank rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
        value = rank.value;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public enum Rank{
        Two(1),
        Three(2),
        Four(3),
        Five(4),
        Six(5),
        Seven(6),
        Eight(7),
        Nine(8),
        Ten(9),
        Jack(10),
        King(11),
        Queen(12),
        Ace(13);
        public final int value;

        private Rank(int value) {
            this.value = value;
        }
    }
    public enum Suit{
        Club,
        Spade,
        Heart,
        Diamond
    }
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(rank.toString());
        builder.append(" of ");
        builder.append(suit.toString());
        return builder.toString();
    }

    public boolean equals(Card card)
    {
        return suit == card.getSuit() &&
                rank == card.getRank();
    }
}
