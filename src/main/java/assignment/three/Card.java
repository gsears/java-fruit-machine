package assignment.three;

public class Card {
    private String name;

    Card(String name) {
        this.name = name.toLowerCase();
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    // Returns the name in a nicely formatted way, with a capital letter.
    @Override
    public String toString() {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Card) {
            Card card = (Card) obj;
            return name.equals(card.getName());
        } else {
            return false;
        }

    }

}
