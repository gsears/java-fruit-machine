package assignment.three;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CardCombination {

    private Map<Card, Integer> cardCountMap = new HashMap<Card, Integer>();
    private int maxCardCount = 0;

    public CardCombination() {
    }

    public CardCombination(Map<Card, Integer> cardCountMap) {
        this.cardCountMap = cardCountMap;

        // Find the new maximum int inside the cardCountMap
        maxCardCount = cardCountMap.entrySet().stream()
                // Get the counts of each card
                .mapToInt(x -> x.getValue())
                // Return the max or 0 if none exists
                .max().orElse(0);
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
            // Set the max count to 0 if there's no other value...
            if (maxCardCount == 0) {
                maxCardCount = 1;
            }
        }
    }

    public int getCount(Card card) {
        return cardCountMap.get(card);
    }

    public boolean contains(Card card) {
        return cardCountMap.containsKey(card);
    }

    public void remove(Card card) {
        cardCountMap.remove(card);
    }

    public void clear() {
        cardCountMap.clear();
        maxCardCount = 0;
    }

    public int getMaxCardCount() {
        return maxCardCount;
    }

    public Map<Card, Integer> getCardCountMap() {
        return cardCountMap;
    }

    /*
     * 
     * FILTER FUNCTIONS ----------------
     * 
     * Return card combinations based on a filter function. Useful for outputing 'winning'
     * combinations, or just the jokers.
     * 
     * CardCombination test = new CardCombination(); test.add(Card.JOKER); test.add(Card.JOKER);
     * test.add(Card.ACE); test.add(Card.QUEEN);
     * 
     * System.out.println(test); // Map: {Queen=1, Ace=1, Joker=2} MaxCount: 2
     * 
     * // Return cards where there are two of a kind CardCombination filteredByCount =
     * test.filterByCount(x -> x == 1); // Map: {Queen=1, Ace=1} MaxCount: 1
     * 
     * // Return cards which have the maximum value CardCombination filteredByCount =
     * test.filterByCount(x -> x == test.getMaxCardCount()); // Map: {Joker=2} MaxCount: 2
     * 
     * // Return cards which are either Jokers or Aces CardCombination filteredByCard =
     * test.filterByCard(x -> x == Card.JOKER || x == Card.ACE); // Map: {Ace=1, Joker=2} MaxCount:
     * 2
     * 
     */
    public CardCombination filterByCount(Predicate<? super Integer> filterFunction) {
        Map<Card, Integer> filteredMap = cardCountMap.entrySet().stream()
                // Filter the stream by the filterFunction acting on counts
                .filter(x -> filterFunction.test(x.getValue()))
                // Collect to a map
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        return new CardCombination(filteredMap);
    }

    public CardCombination filterByCard(Predicate<? super Card> filterFunction) {
        Map<Card, Integer> filteredMap = cardCountMap.entrySet().stream()
                // Filter the stream by the filterFunction acting on Cards
                .filter(x -> filterFunction.test(x.getKey()))
                // Collect to a map
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        return new CardCombination(filteredMap);
    }

    @Override
    public String toString() {
        return String.format("%s Map: %s MaxCount: %d", super.toString(), cardCountMap.toString(),
                maxCardCount);
    }

}
