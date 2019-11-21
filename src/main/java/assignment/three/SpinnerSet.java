package assignment.three;

public class SpinnerSet {

    private int count;
    private Spinner[] spinners;
    private Card[] cards;

    // This object stores the card counts
    CardCombination cardCounts = new CardCombination();

    public SpinnerSet(int count) {
        this.count = count;
        // Allocate spinner array
        spinners = new Spinner[count];
        // Allocate card array
        cards = new Card[count];

        // Create spinner instances
        for (int i = 0; i < count; i++) {
            spinners[i] = new Spinner();
        }

        // initialise the spinners
        spin();
    }

    public void spin() {
        // Reset count cache
        cardCounts.clear();

        for (int i = 0; i < count; i++) {
            // Get each spinner
            Spinner spinner = spinners[i];
            spinner.spin();

            // Get the new value
            Card newCard = spinner.getCard();
            // Add to the card counter cache.
            cardCounts.add(newCard);
            // Push the card to the stored array
            cards[i] = newCard;
        }
    }

    public Card[] getCards() {
        return cards;
    }

    public CardCombination getCardCounts() {
        return cardCounts;
    }

    // Returns the number of spinners
    public int getSpinnerCount() {
        return count;
    }
}
