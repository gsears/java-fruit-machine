package assignment.three;

public class Spinner {

    private Card card;

    public Spinner() {
        // Initialise with a random card.
        spin();
    }

    // Can optionally initialise with a specific card
    public Spinner(Card card) {
        setCard(card);
    }

    public Card spin() {
        setCard(Card.getRandomCard());
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
