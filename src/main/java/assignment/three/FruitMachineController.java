package assignment.three;

import java.util.Map;

public class FruitMachineController
        implements BalanceObserver, SpinnerSetObserver, GameStateObserver {

    private static final String INITIAL_MESSAGE = "Welcome!";
    private static final String VICTORY_MESSAGE = "You win!";
    private static final String LOSE_MESSAGE = "You Lose.";

    private FruitMachineView view;
    private FruitMachineInterface model;

    FruitMachineController(FruitMachineInterface model) {
        this.model = model;
        // Register the controller to observe the relevant model state changes.
        model.registerObserver((BalanceObserver) this);
        model.registerObserver((SpinnerSetObserver) this);
        model.registerObserver((GameStateObserver) this);
    }

    public void addView(FruitMachineView view) {
        this.view = view;
        // Update all view components from the model
        updateAll();
    }

    public void spin() {
        model.spin();
    }

    public void reset() {
        model.reset();
    }

    // Used to set the number of spinners in the view
    public int getSpinnerCount() {
        return model.getSpinnerCount();
    }

    // Enable the spin button, disable the 'reset' button.
    public void enablePlay() {
        view.setEnabledNewGameButton(false);
        view.setEnabledSpinButton(true);
    }

    // Disable the spin button, for game over states.
    // Enable the 'reset' button.
    public void disablePlay() {
        view.setEnabledNewGameButton(true);
        view.setEnabledSpinButton(false);
    }

    // Update the view's message display
    public void setMessageDisplay(String message) {
        view.setMessageDisplay(message);
    }

    // Update the view's victory display
    public void setVictoryDisplay(String message) {
        view.setVictoryDisplay(message);
    }

    // Update ALL view components (simulates a complete
    // state change in the model).
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
            setVictoryDisplay(VICTORY_MESSAGE); // Display victory message
        }
        if (gameState == GameState.LOST) {
            disablePlay(); // Disable play button
            setVictoryDisplay(LOSE_MESSAGE); // Display lose message
        }

        // This is only fired when the model resets, i.e. at the start of a
        // new game.
        if (gameState == GameState.PLAY) {
            enablePlay(); // Enable spin buttons
            setMessageDisplay(INITIAL_MESSAGE); // Set a welcome message
            setVictoryDisplay(""); // Reset the victory display
        }
    }

    // When the model spinners change, this method is called.
    // It is used to set the cards and also the display message
    // based on the card combination.
    @Override
    public void updateSpinners() {

        // SET THE VIEW CARD VALUES
        // ------------------------

        // Get the spinner values from the model
        Card[] cards = model.getCards();
        // Set the card texts in the view
        view.setSpinners(cards);

        // SET THE VIEW MESSAGE
        // --------------------

        // Get the current 'scoring' card counts from the display
        CardCombination lastScoringCombo = model.getLastScoringCombo();

        // If they exist (if the game is initialised for the first time, they won't...)
        if (lastScoringCombo != null) {

            // Generate the payout text.
            int lastPayout = model.getLastPayout();
            String payoutString;

            if (lastPayout == 0) {
                payoutString = "No score";
            } else if (lastPayout > 0) {
                payoutString = String.format("You win £%d", lastPayout);
            } else {
                payoutString = String.format("You lose £%d", Math.abs(lastPayout));
            }

            // Convert the 'scoring' count cards into nicely formatted prose.
            String lastScoringComboString = convertScoringComboToText(lastScoringCombo);
            // Combine the 'scoring' card combo with the payout and set the view
            setMessageDisplay(String.format("%s. %s.", lastScoringComboString, payoutString));
        }

    }

    // When the model balance is changed, this method is called.
    // There is a subtle difference with spinners, as this will not
    // be called if the 'payout' is 0 to avoid redundant updates.
    @Override
    public void updateBalance() {
        view.setBalanceDisplay("Balance is: £" + model.getPlayerBalance());
    }

    // This private method converts a scoring combination (the relevant cards and
    // their counts) to an appropriate message, as the model shouldn't know anything
    // about the how the data is displayed.
    private String convertScoringComboToText(CardCombination scoringCombo) {
        // Start the string with this.
        String output = "Combo: ";

        // For each card / count combination
        for (Map.Entry<Card, Integer> entry : scoringCombo.getCardCountMap().entrySet()) {
            // Get the count
            int count = entry.getValue();
            // Get the card, and convert it to a string
            String cardString = entry.getKey().toString();

            // Make the card plural if there's more than one
            if (count != 1) {
                cardString = cardString + "s";
            }

            // Append the output to the current list.
            output = output + String.format("%d %s, ", count, cardString);

        }
        // Remove the comma and space at the end of the string.
        return output.substring(0, output.length() - 2);
    }


}
