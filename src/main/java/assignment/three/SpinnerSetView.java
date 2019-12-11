package assignment.three;

import java.awt.GridLayout;

/**
 * SpinnerSetView.java - Gareth Sears | 2493194
 * 
 * A PaddedJPanel which contains a number of SpinnerViews. SIZE sets this number.
 * Can be set by passing it a Card[].
 */

@SuppressWarnings("serial")
public class SpinnerSetView extends PaddedJPanel {

    // The padding between the spinners
    private static final int SPINNER_PADDING = 10;

    private int size; // How many spinners there are.
    private SpinnerView[] spinnerViewArray;

    SpinnerSetView(int size) {
        this.size = size;

        // Creates on a horizontal grid with padding between grid locations.
        setLayout(new GridLayout(1, size, SPINNER_PADDING, SPINNER_PADDING));
        createSpinnerViews();
    }

    void createSpinnerViews() {
        spinnerViewArray = new SpinnerView[size];

        for (int i = 0; i < size; i++) {
            SpinnerView spinnerView = new SpinnerView();
            spinnerViewArray[i] = spinnerView;
            add(spinnerView);
        }
    }

    // Set the displays from a card array.
    void setSpinners(Card[] cards) {
        for (int i = 0; i < size; i++) {
            spinnerViewArray[i].setText(cards[i].toString());
        }
    }
}
