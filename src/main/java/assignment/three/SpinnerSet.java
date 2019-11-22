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

    CardCounts cardCounts = new CardCounts(); // Card count cache

    public SpinnerSet(int count) {
        this.count = count;
        spinners = new Spinner[count];
        cards = new Card[count];

        // Create spinner instances
        for (int i = 0; i < count; i++) {
            spinners[i] = new Spinner();
        }

        spin(); // initialise the spinners with random values
    }

    public void spin() {

        cardCounts.clear(); // Reset card count cache

        for (int i = 0; i < count; i++) {
            Spinner spinner = spinners[i]; // Get each spinner
            Card newCard = spinner.spin(); // Spin and get the value
            cardCounts.add(newCard); // Add to the card count cache.
            cards[i] = newCard; // Set the internal card array
        }
    }

    // Get the cards shown on the spinners
    public Card[] getCards() {
        return cards;
    }

    // Get the card count cache
    public CardCounts getCardCounts() {
        return cardCounts;
    }
}
