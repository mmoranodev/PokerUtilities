public class Card {
    private Rank rank;
    private Suit suit;
    private int value;

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
        One(1),
        Two(2),
        Three(3),
        Four(4),
        Five(5),
        Six(6),
        Seven(7),
        Eight(8),
        Nine(9),
        Ten(10),
        Jack(11),
        King(12),
        Queen(13),
        Ace(14);
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
        return this.value == card.value;
    }
}
