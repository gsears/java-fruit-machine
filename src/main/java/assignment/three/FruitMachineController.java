package assignment.three;

import java.util.Arrays;

public class FruitMachineController
        implements BalanceObserver, SpinnerSetObserver, GameStateObserver {

    private static final String INITIAL_MESSAGE = "Welcome!";
    private static final String VICTORY_MESSAGE = "You win! :)";
    private static final String LOSE_MESSAGE = "You Lose. :(";

    private FruitMachineView view;
    private FruitMachineInterface model;

    FruitMachineController(FruitMachineInterface model) {
        this.model = model;
        // Register the controller to observe the relevant model state
        // changes.
        model.registerObserver((BalanceObserver) this);
        model.registerObserver((SpinnerSetObserver) this);
        model.registerObserver((GameStateObserver) this);
    }

    public void spin() {
        System.out.println(model + "\n\n\n");

        model.spin();
    }

    public void addView(FruitMachineView view) {
        this.view = view;
        updateAll();
    }

    public void reset() {
        model.reset();
    }

    public int getSpinnerCount() {
        return model.getSpinnerCount();
    }

    public void enablePlay() {
        view.setEnabledNewGameButton(false);
        view.setEnabledSpinButton(true);
    }

    public void disablePlay() {
        view.setEnabledNewGameButton(true);
        view.setEnabledSpinButton(false);
    }

    public void setMessageDisplay(String message) {
        view.setMessageDisplay(message);
    }

    public void setVictoryDisplay(String message) {
        view.setVictoryDisplay(message);
    }

    public void updateAll() {
        updateGameState();
        updateSpinners();
        updateBalance();
    }

    @Override
    public void updateGameState() {
        GameState gameState = model.getGameState();
        if (gameState == GameState.WON) {
            disablePlay();
            setVictoryDisplay(VICTORY_MESSAGE);
        }
        if (gameState == GameState.LOST) {
            disablePlay();
            setVictoryDisplay(LOSE_MESSAGE);
        }
        if (gameState == GameState.PLAY) {
            enablePlay();
            setMessageDisplay(INITIAL_MESSAGE);
            setVictoryDisplay("");
        }
    }

    @Override
    public void updateSpinners() {
        setMessageDisplay("Spin!");
        String[] texts =
                Arrays.stream(model.getCards()).map(c -> c.toString()).toArray(String[]::new);
        view.setCardTexts(texts);

    }

    @Override
    public void updateBalance() {
        view.setBalanceDisplay("Balance is: " + model.getBalance());
    }


}
