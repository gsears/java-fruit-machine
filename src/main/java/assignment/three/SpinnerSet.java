package assignment.three;

public class SpinnerSet {

    private int count;
    private Spinner[] spinners;
    private Card[] cards;

    // This object stores the card counts
    CardCombination cardCounts = new CardCombination();

    public SpinnerSet(int count) {
        this.count = count;

        // Set the internal spinners and their displayed cards
        spinners = new Spinner[count];
        cards = new Card[count];

        // Create spinner instances
        for (int i = 0; i < count; i++) {
            spinners[i] = new Spinner();
        }

        // initialise the spinners
        spin();
    }

    public void spin() {
        // Reset card count cache
        cardCounts.clear();

        for (int i = 0; i < count; i++) {
            // Get each spinner
            Spinner spinner = spinners[i];
            spinner.spin();

            // Get the new value
            Card newCard = spinner.getCard();
            // Add to the card counter cache.
            cardCounts.add(newCard);
            // Push the card to card array
            cards[i] = newCard;
        }
    }

    // Get the cards shown on the spinners
    public Card[] getCards() {
        return cards;
    }

    // Get the object that holds the card counts (for scoring combinations etc.)
    public CardCombination getCardCounts() {
        return cardCounts;
    }
}
