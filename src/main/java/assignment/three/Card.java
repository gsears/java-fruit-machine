package assignment.three;

/**
 * Card.java - Gareth Sears | 2493194
 * 
 * A class which represents a 'card' on a fruit machine spinner. This class was created so that
 * different machines can use different card sets.
 */

public class Card {
    private String name;

    Card(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Override hashCode so that it can be stored in the hashmap used by CardCounts
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    // Returns the name in a nicely formatted way, with a capital letter.
    @Override
    public String toString() {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    // Compare cards based on their name.
    @Override
    public boolean equals(Object obj) {
        // If the object isn't null and is a card...
        if (obj != null && obj instanceof Card) {
            // ...check if the name Strings are equal.
            Card card = (Card) obj;
            return name.equals(card.getName());
        } else {
            return false;
        }
    }
}
