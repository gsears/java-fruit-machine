package assignment.three;


public interface FruitMachineInterface {

    public void spin();

    public void setPayouts(Payouts payouts);

    public Card[] getCards();

    public int getSpinnerCount();

    public GameState getGameState();

    public int getPlayerBalance();

    public CardCombination getLastScoringCombo();

    public int getLastPayout();

    public void reset();

    public void registerObserver(GameStateObserver o);

    public void registerObserver(BalanceObserver o);

    public void registerObserver(SpinnerSetObserver o);

    public void removeObserver(GameStateObserver o);

    public void removeObserver(BalanceObserver o);

    public void removeObserver(SpinnerSetObserver o);
}
