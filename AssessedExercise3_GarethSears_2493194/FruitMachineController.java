import java.util.Map;

/**
 * FruitMachineController.java | Gareth Sears - 2493194
 * 
 * A controller class that detects different changes to the model data via an observer pattern and
 * updates the view accordingly by 'pulling' the data from said model (as opposed to being 'pushed'
 * an event object).
 * 
 * It implements different observers because different aspects of the model change at different
 * frequencies. This reduces 'redundant' updates. This implementation of MVC is based on that found
 * in Head First Design Patterns
 * (https://www.oreilly.com/library/view/head-first-design/0596007124/).
 * 
 * It also handles converting the data into nice string formats. This is because the model shouldn't
 * care about the way the data is formatted and the view should be as 'dumb' as possible, its job is
 * to display what it is told to...
 */

public class FruitMachineController
        implements BalanceObserver, SpinnerSetObserver, GameStateObserver {

    private static final String WELCOME_MESSAGE = "Welcome!";
    private static final String VICTORY_MESSAGE = "You win!";
    private static final String LOSE_MESSAGE = "You lose.";

    private FruitMachineModel model;
    private FruitMachineView view;

    // CONSTRUCTOR
    FruitMachineController(FruitMachineModel model) {
        this.model = model;
        // Register the controller to observe the relevant model state changes.
        model.registerObserver((BalanceObserver) this);
        model.registerObserver((SpinnerSetObserver) this);
        model.registerObserver((GameStateObserver) this);
    }

    // PUBLIC METHODS for VIEW
    public void addView(FruitMachineView view) {
        this.view = view;
        updateAll(); // Pull data from the model on add to update the view.
    }

    public void spin() {
        model.spin();
    }

    public void reset() {
        model.reset();
    }

    // Used to set the number of spinners in the view (can be variable).
    public int getSpinnerCount() {
        return model.getSpinnerCount();
    }

    // PUBLIC METHODS for OBSERVER UPDATES

    // Disable / enable buttons and set displays on gameState changes.
    @Override
    public void updateGameState() {
        try {
            switch (model.getGameState()) {
                case WON:
                    disablePlay();
                    view.setVictoryDisplay(VICTORY_MESSAGE);
                    break;
                case LOST:
                    disablePlay();
                    view.setVictoryDisplay(LOSE_MESSAGE);
                    break;
                case PLAY:
                    enablePlay(); // Enable spin buttons
                    view.setMessageDisplay(WELCOME_MESSAGE);
                    view.setVictoryDisplay("");
                    break;
                default:
                    throw new Exception(String.format(
                            "State %s has not been set in FruitMachineController.updateGameState",
                            model.getGameState()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSpinners() {

        // UPDATE THE VIEW SPINNERS
        view.setSpinners(model.getCards());

        // UPDATE THE DISPLAY MESSAGE
        CardCounts cardCounts = model.getLastScoringCardCounts();

        // If a scoring combo exists (if the app is initialised for the first time, it doesn't...)
        if (cardCounts != null) {

            // CREATE PAYOUT DISPLAY STRING
            int lastPayout = model.getLastPayout();
            String payoutString;

            if (lastPayout == 0) {
                payoutString = "No score";
            } else if (lastPayout > 0) {
                payoutString = String.format("You win £%d", lastPayout);
            } else {
                payoutString = String.format("You lose £%d", Math.abs(lastPayout));
            }

            // CREATE CARD COUNT DISPLAY STRING (see private method below)
            String cardCountString = convertCardCountsToText(cardCounts);

            // SET THE MESSAGE DISPLAY to show CARD COUNTS and PAYOUT
            view.setMessageDisplay(String.format("%s. %s.", cardCountString, payoutString));
        }
    }

    @Override
    public void updateBalance() {
        view.setBalanceDisplay("Balance is: £" + model.getPlayerBalance());
    }

    // PRIVATE HELPER METHODS

    // Enable the spin button, disable the 'reset' button.
    private void enablePlay() {
        view.setEnabledNewGameButton(false);
        view.setEnabledSpinButton(true);
    }

    // Disable the spin button, for game over states. Enable the 'reset' button.
    private void disablePlay() {
        view.setEnabledNewGameButton(true);
        view.setEnabledSpinButton(false);
    }

    // Pull all the data from the model (for resets / initialisation).
    private void updateAll() {
        updateGameState();
        updateSpinners();
        updateBalance();
    }

    // This private method converts a scoring combination (the relevant cards and their counts) to
    // an appropriate message. This is done in the controller because the model shouldn't consider
    // anything to do with display, including string formatting.
    private String convertCardCountsToText(CardCounts cardCounts) {

        String output = "Combo: "; // Start the string with this.

        // For each card / count combination
        for (Map.Entry<Card, Integer> entry : cardCounts.getEntrySet()) {
            int count = entry.getValue(); // Get the card count
            String cardString = entry.getKey().toString(); // Convert the card to a string.

            if (count != 1) {
                cardString = cardString + "s"; // Make the card plural if there's more than one
            }

            output = output + String.format("%d %s, ", count, cardString); // Append to output.
        }

        return output.substring(0, output.length() - 2); // Remove the final comma and space.
    }
}
