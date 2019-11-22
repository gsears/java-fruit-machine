package assignment.three;

/**
 * SpinnerSet.java | Gareth Sears - 2493194
 * 
 * Contains a set of spinners, representing the 'meat' of a fruit machine! Stores various objects
 * for outside classes to interact with.
 * 
 * The most important of these are the card array (the array of what is actually displayed) and a
 * CardCombination which stores the card counts in a cache for processing to calculate winning
 * combos and the such.
 */

public class SpinnerSet {

    private int count;
    private Spinner[] spinners;
    private Card[] cards;

    // This object stores the card counts
    CardCombination cardCounts = new CardCombination();

    public SpinnerSet(int count) {
        this.count = count;

        // Initialise based on how many spinners we want
        spinners = new Spinner[count];
        cards = new Card[count];

        // Create spinner instances
        for (int i = 0; i < count; i++) {
            spinners[i] = new Spinner();
        }

        spin(); // initialise the spinners with random values
    }

    public void spin() {
        // Reset card count cache
        cardCounts.clear();

        for (int i = 0; i < count; i++) {
            // Get each spinner
            Spinner spinner = spinners[i];
            // Spin and get the value
            Card newCard = spinner.spin();
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
