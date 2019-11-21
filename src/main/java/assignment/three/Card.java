package assignment.three;

// The card combinations as enums
public enum Card {
    JOKER, JACK, QUEEN, KING, ACE;

    // Returns the name in a nicely formatted way, with a capital letter.
    @Override
    public String toString() {
        String name = name();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
