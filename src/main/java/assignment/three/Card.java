package assignment.three;

/**
 * Card.Java | Gareth Sears - 2493194
 * 
 * This enum represents the different card values. Using an enum adds clarity to the source code and
 * allows for easy modifications.
 */

public enum Card {
    JOKER, JACK, QUEEN, KING, ACE;

    // Returns the name in a nicely formatted way, with a capital letter.
    @Override
    public String toString() {
        String name = name();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
