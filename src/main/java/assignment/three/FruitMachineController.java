package assignment.three;

import java.util.Map;

/**
 * FruitMachineController.java | Gareth Sears - 2493194
 * 
 * A controller class that detects different changes to the model data via an observer pattern and
 * updates the view accordingly.
 * 
 * It observes different aspects of the model that change at different frequencies to avoid
 * redundant operations (i.e. the spinner is updated more frequently than the balance, and the
 * balance is updated more frequently than the game state.)
 * 
 * It also handles converting the data into nice string formats. This is because the model shouldn't
 * care about the way the data is formatted and the view should be as 'dumb' as possible, its job is
 * to display what it is told to...not decide 'how' to change it.
 */
public class FruitMachineController
        implements BalanceObserver, SpinnerSetObserver, GameStateObserver {

    private static final String WELCOME_MESSAGE = "Welcome!";
    private static final String VICTORY_MESSAGE = "You win!";
    private static final String LOSE_MESSAGE = "You lose.";

    // Model uses an interface, as we only need to ensure certain functionality is met.
    // "Futureproofs" our controller for any drastic model refactors.
    private FruitMachineInterface model;
    private FruitMachineView view;

    FruitMachineController(FruitMachineInterface model) {
        this.model = model;
        // Register the controller to observe the relevant model state changes.
        model.registerObserver((BalanceObserver) this);
        model.registerObserver((SpinnerSetObserver) this);
        model.registerObserver((GameStateObserver) this);
    }

    public void addView(FruitMachineView view) {
        this.view = view;
        updateAll(); // Initialise view components from the model data on add.
    }

    public void spin() {
        model.spin();
    }

    public void reset() {
        model.reset();
    }

    // Used to set the number of spinners in the view.
    public int getSpinnerCount() {
        return model.getSpinnerCount();
    }

    // Enable the spin button, disable the 'reset' button.
    public void enablePlay() {
        view.setEnabledNewGameButton(false);
        view.setEnabledSpinButton(true);
    }

    // Disable the spin button, for game over states. Enable the 'reset' button.
    public void disablePlay() {
        view.setEnabledNewGameButton(true);
        view.setEnabledSpinButton(false);
    }

    // Update ALL view components (for resets / initialisation).
    public void updateAll() {
        updateGameState();
        updateSpinners();
        updateBalance();
    }

    // When the model changes game state, this method is called.
    @Override
    public void updateGameState() {
        GameState gameState = model.getGameState();

        if (gameState == GameState.WON) {
            disablePlay(); // Disable play button
            view.setVictoryDisplay(VICTORY_MESSAGE); // Display victory message
        }
        if (gameState == GameState.LOST) {
            disablePlay(); // Disable play button
            view.setVictoryDisplay(LOSE_MESSAGE); // Display lose message
        }

        // This is only fired when the model resets, i.e. at the start of a new game.
        if (gameState == GameState.PLAY) {
            enablePlay(); // Enable spin buttons
            view.setMessageDisplay(WELCOME_MESSAGE);
            view.setVictoryDisplay("");
        }
    }

    // When the model spinners change, this method is called. It is used to set the cards and also
    // the display message based on the card combination.
    @Override
    public void updateSpinners() {

        // SET THE CARDS
        Card[] cards = model.getCards(); // Get the spinner values from the model
        view.setSpinners(cards); // Set the card texts in the view

        // SET THE DISPLAY
        CardCounts cardCounts = model.getLastScoringCounts();

        // If a scoring combo exists (if the game is initialised for the first time, it doesn't...)
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

            // CREATE CARD COUNT DISPLAY STRING
            String cardCountString = convertCardCountsToText(cardCounts);

            // SET THE MESSAGE DISPLAY to CARD COUNTS and PAYOUT
            view.setMessageDisplay(String.format("%s. %s.", cardCountString, payoutString));
        }
    }

    // When the model balance is changed, this method is called. There is a subtle difference with
    // spinners, as this will not be called if the 'payout' is 0 to avoid redundant updates.
    @Override
    public void updateBalance() {
        view.setBalanceDisplay("Balance is: £" + model.getPlayerBalance());
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
