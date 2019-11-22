package assignment.three;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * CardCombination.java | Gareth Sears - 2493194
 * 
 * This class is used for caching card counts and returning them in useful forms. The rationale was
 * that different combinations of card counts are what pays out, rather than 'in a row', and this
 * class would be useful for dealing with any combination of cards, avoiding hard coded win
 * conditions. It is designed to work with the Payouts class, which returns the appropriate 'payout'
 * for the appropriate combination.
 * 
 * Fundamentally, it is an interface to a HashMap with some useful helper functions. For example,
 * the ability to filter by card or count. Storing these in a class also means a consistant
 * interface with controlers outside the model.
 * 
 * EXAMPLE USAGE:
 * 
 * CardCombination cardCounter = new CardCombination(); 
 * cardCounter.add(Card.JOKER);
 * cardCounter.add(Card.JOKER); 
 * cardCounter.add(Card.ACE); 
 * cardCounter.add(Card.QUEEN);
 * 
 * System.out.println(cardCounter); // Map: {Queen=1, Ace=1, Joker=2} MaxCount: 2
 * 
 * CardCombination cardsWithCountOfOne = cardCounter.filterByCount(x -> x == 1); 
 * // Map: {Queen=1, Ace=1} MaxCount: 1
 * 
 * CardCombination cardsWithMaxCount = cardCounter.filterByCount(x -> x == cardCounter.getMaxCardCount()); 
 * // Map: {Joker=2} MaxCount: 2
 * 
 * CardCombination jokerAndAceCardCounts = cardCounter.filterByCard(x -> x == Card.JOKER || x == Card.ACE); 
 * // Map: {Ace=1, Joker=2} MaxCount:
 * 
 */
public class CardCombination {

    private Map<Card, Integer> cardCountMap = new HashMap<Card, Integer>();
    private int maxCardCount = 0;

    public CardCombination() {
    }

    // This is private because it is only used to generate new card combos following 
    // filter operations. This immutability avoids mutating the original accidentally.
    private CardCombination(Map<Card, Integer> cardCountMap) {
        this.cardCountMap = cardCountMap;
        // Traverse the new map and set the maximum card count.
        setMaximum(); 
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

    // Return the map for any further processing.
    public Map<Card, Integer> getCardCountMap() {
        return cardCountMap;
    }

    // Scan through the current map and get the current maximum card count.
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

    public CardCombination filterByCount(Predicate<? super Integer> filterFunction) {
        Map<Card, Integer> filteredMap = cardCountMap.entrySet().stream()
                // Filter the stream by the filterFunction acting on counts
                .filter(x -> filterFunction.test(x.getValue()))
                // Collect to a map
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        // Immutable
        return new CardCombination(filteredMap);
    }

    // Use a lambda function to get specific combinations of cards by their counts.
    // This would be useful for getting scoring combinations related to a specific card.
    // In the assignment's case: Card.JOKER.

    public CardCombination filterByCard(Predicate<? super Card> filterFunction) {
        Map<Card, Integer> filteredMap = cardCountMap.entrySet().stream()
                // Filter the stream by the filterFunction acting on cards
                .filter(x -> filterFunction.test(x.getKey()))
                // Collect to a map
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        // Immutable
        return new CardCombination(filteredMap);
    }

    // For debugging.
    @Override
    public String toString() {
        return String.format("%s Map: %s MaxCount: %d", super.toString(), cardCountMap.toString(),
                maxCardCount);
    }

}
