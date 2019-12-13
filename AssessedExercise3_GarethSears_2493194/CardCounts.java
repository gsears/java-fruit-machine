import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * CardCounts.java | Gareth Sears - 2493194
 * 
 * This class is used for caching card counts in spinners and returning them in useful forms.
 * 
 * Fundamentally, it is an interface to a HashMap with some useful helper methods. For example, the
 * ability to filter by card or count (in the assignment's case, getting any combinations that
 * contain a joker, or the highest card counts). This provides a useful mechanism for passing data 
 * to controllers outside the model, and filtering the sets so that 'winning' results are shown.
 * 
 * It is also more efficient than iterating through an array to get winning combos O(n) -> O(1).
 * 
 * EXAMPLE USAGE:
 * 
 * CardCounts cardCounts = new CardCounts()
 *  cardCounts.add(new Card("Joker"))
 *  cardCounts.add(new Card("Joker"))
 *  cardCounts.add(new Card("Ace"))
 *  cardCounts.add(new Card("Queen"));
 * 
 * System.out.println(cardCounts); // Map: {Queen=1, Ace=1, Joker=2} MaxCount: 2
 * 
 * CardCounts cardsWithCountOfOne = cardCounts.filterByCount(x -> x == 1); 
 * // Map: {Queen=1, Ace=1} MaxCount: 1
 * 
 * CardCounts cardsWithMaxCount = cardCounts.filterByCount(x -> x == cardCounts.getMaxCardCount());
 * // Map: {Joker=2} MaxCount: 2;
 * 
 * CardCounts jokerAndAceCardCounts = cardCounts.filterByCard(x -> x == Card.JOKER || x == Card.ACE); 
 * // Map: {Ace=1, Joker=2} MaxCount: 2
 */

public class CardCounts {

    private Map<Card, Integer> cardCountMap = new HashMap<Card, Integer>();
    private int maxCardCount = 0;

    // Constructor
    public CardCounts() {
    }

    public void add(Card card) {
        // If the card is in our cache
        if (cardCountMap.containsKey(card)) {
            // Get the current card count + 1
            int newCardCount = cardCountMap.get(card) + 1;
            // If the new card count is the maximum, update the maximum value
            if (newCardCount > maxCardCount) {
                maxCardCount = newCardCount;
            }
            // Set the new count in the cache
            cardCountMap.put(card, newCardCount);
        } else {
            // Add the card to the cache and set its count to one.
            cardCountMap.put(card, 1);
            // Set the max count to 1 if its the first card added.
            if (maxCardCount == 0) {
                maxCardCount = 1;
            }
        }
    }

    // Get the count of a particular card
    public int getCount(Card card) {
        return cardCountMap.get(card);
    }

    // Check to see if a particular card is present in the set (i.e. JOKER)
    public boolean contains(Card card) {
        return cardCountMap.containsKey(card);
    }

    // Remove a card from the set.
    public void remove(Card card) {
        if (cardCountMap.get(card) == maxCardCount) {
            cardCountMap.remove(card);
            // Maybe we deleted the card with the maximum count?
            // Reset it to be sure.
            setMaximum();
        } else {
            cardCountMap.remove(card);
        }
    }

    // Clear the cache contents and reset the max count.
    public void clear() {
        cardCountMap.clear();
        maxCardCount = 0;
    }

    // Get the current maximum count.
    public int getMaxCardCount() {
        return maxCardCount;
    }

    // Return the entries for any further processing (useful in the controller for formatting the
    // data into nice message strings).
    public Set<Map.Entry<Card, Integer>> getEntrySet() {
        return cardCountMap.entrySet();
    }


    // Scan through the current map and get the current maximum card count.
    // Used to reset the maximum on removal of a card.
    private void setMaximum() {
        maxCardCount = cardCountMap.entrySet().stream()
                // Get the counts of each card
                .mapToInt(x -> x.getValue())
                // Return the max or 0 if none exists
                .max().orElse(0);
    }

    // Use a lambda function to get specific combinations of cards by their counts.
    // This would be useful for getting scoring combinations related to a specific count.
    // In the assignment's case, the maximum card count(s).
    // e.g.
    // cardCounts.filterByCount(x -> x == cardCounts.getMaxCardCount());

    public CardCounts filterByCount(Predicate<? super Integer> filterFunction) {
        Map<Card, Integer> filteredMap = cardCountMap.entrySet().stream()
                // Filter the stream by the filterFunction acting on counts
                .filter(x -> filterFunction.test(x.getValue()))
                // Collect to a map
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        // Immutable
        return new CardCounts(filteredMap);
    }

    // Use a lambda function to get specific combinations of cards by their counts.
    // This would be useful for getting scoring combinations related to a specific card.
    // In the assignment's case: Card.JOKER.
    // e.g.
    // cardCounts.filterByCard(x -> x == (new Card("Joker"))

    public CardCounts filterByCard(Predicate<? super Card> filterFunction) {
        Map<Card, Integer> filteredMap = cardCountMap.entrySet().stream()
                // Filter the stream by the filterFunction acting on cards
                .filter(x -> filterFunction.test(x.getKey()))
                // Collect to a map
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        // Immutable
        return new CardCounts(filteredMap);
    }

    // For debugging.
    @Override
    public String toString() {
        return String.format("%s Map: %s MaxCount: %d", super.toString(), cardCountMap.toString(),
                maxCardCount);
    }

    // Constructor
    // This is a private constructor used to return new card combos following filter operations. 
    // Avoids mutating the original hashmap accidentally.
    private CardCounts(Map<Card, Integer> cardCountMap) {
        this.cardCountMap = cardCountMap;
        setMaximum(); // Traverse the new map and set the maximum card count.
    }

}
