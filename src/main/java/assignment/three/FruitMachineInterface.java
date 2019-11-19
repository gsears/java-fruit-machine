package assignment.three;

import java.util.Map;

// Notifies controllers of changes through java beans PropertyChangeSupport.
public interface FruitMachineInterface {

    public void spin();

    public void setPayouts(Payouts payouts);

    public Card[] getCards();

    // public Map<Card, Integer> getCardCounts();

    public int getSpinnerCount();

    public GameState getGameState();

    public int getBalance();

    public void reset();

    public void registerObserver(GameStateObserver o);

    public void registerObserver(BalanceObserver o);

    public void registerObserver(SpinnerSetObserver o);

    // TODO: Add RemoveObserver Functions

}
