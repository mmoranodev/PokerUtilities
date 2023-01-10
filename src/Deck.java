import java.util.Random;

public class Deck {
    private Card[] deck;
    private int currentCard;

    public Deck()
    {
        currentCard = -1;
        int pos = 0;
        deck = new Card[52];
        for(Card.Suit suit : Card.Suit.values()){
            for(Card.Rank rank : Card.Rank.values()){
                deck[pos] = new Card(rank, suit);
                pos++;
            }
        }
    }
    //returns next card
    public Card deal()
    {
        currentCard++;
        return deck[currentCard];
    }
    //shuffles deck
    public void shuffle()
    {
        for(int i = 0; i < 52; i++)
        {
            Random random = new Random();
            int y = random.nextInt(52);
            Card x = deck[i];
            deck[i] = deck[y];
            deck[y] = x;
        }
    }

    //resets deck
    public void reset(){
        int pos = 0;
        currentCard = -1;
        deck = new Card[52];
        for(Card.Suit suit : Card.Suit.values()){
            for(Card.Rank rank : Card.Rank.values()){
                deck[pos] = new Card(rank, suit);
            }
        }
    }


}
