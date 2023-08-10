import java.io.Serializable;

public class Player implements Serializable {
    private Card card1, card2;
    private int money, bet;
    private boolean isAllIn, hasFolded, inSidePot, isBankrupt;
    private String name;
    private HandRanker.HandRank handRank;

    public Player(String name)
    {
        this.name = name;
    }

    public void setHand(Card c1, Card c2)
    {
        card1 = c1;
        card2 = c2;
        sortHand();
    }

    public void sortHand(){
        Card temp;
        if(card1.getValue() < card2.getValue()){
            temp = card1;
            card1 = card2;
            card2 = temp;
        }
    }
    public String showHand(){
        StringBuilder builder = new StringBuilder();
        builder.append(card1.toString());
        builder.append(", ");
        builder.append(card2.toString());
        return builder.toString();
    }

    public String showHandAndMoney()
    {
        return "Hand: " + showHand() + ", Money: " + money;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public boolean isAllIn() {
        return isAllIn;
    }

    public void setAllIn(boolean allIn) {
        isAllIn = allIn;
    }

    public boolean hasFolded() {
        return hasFolded;
    }

    public void setFolded(boolean hasFolded) {
        this.hasFolded = hasFolded;
    }

    public boolean inSidePot() {
        return inSidePot;
    }

    public void setInSidePot(boolean inSidePot) {
        this.inSidePot = inSidePot;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HandRanker.HandRank getHandRank() {
        return handRank;
    }

    public void setHandRank(HandRanker.HandRank handRank) {
        this.handRank = handRank;
    }
}
