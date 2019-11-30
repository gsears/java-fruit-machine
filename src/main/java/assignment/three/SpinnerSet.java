package assignment.three;

/**
 * SpinnerSet.java | Gareth Sears - 2493194
 * 
 * Contains a set of spinners, representing the 'meat' of a fruit machine! Stores various objects
 * for outside classes to interact with.
 * 
 * The most important of these are the card array (an array of what is actually displayed) and a
 * CardCount object which stores the card counts in a cache to calculate winning combos and for
 * displays.
 */

public class SpinnerSet {

    private int count;
    private Spinner[] spinners;
    private Card[] cards;

    CardCounts cardCounts = new CardCounts(); // Card count cache

    public SpinnerSet(int count, Card[] cardArray) {
        this.count = count;

        // Initialise 'count' number of spinners.
        spinners = new Spinner[count];
        cards = new Card[count];

        // Create spinner instances.
        for (int i = 0; i < count; i++) {
            spinners[i] = new Spinner(cardArray);
        }

        spin(); // Initialise card array and counts.
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
