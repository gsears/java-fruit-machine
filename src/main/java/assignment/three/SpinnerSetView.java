package assignment.three;

import java.awt.GridLayout;

/**
 * SpinnerSetView.java - Gareth Sears | 2493194
 * 
 * This view encapsulates the individual spinner card views and is used for creating fruit machines
 * where the number of spinners can vary.
 */

@SuppressWarnings("serial")
public class SpinnerSetView extends PaddedJPanel {

    // The padding between the spinners
    private static final int SPINNER_PADDING = 10;

    int size; // How many spinners there are.
    SpinnerView[] SpinnerViews;

    SpinnerSetView(int size) {
        // Create on a horizontal grid with padding between grid locations.
        setLayout(new GridLayout(1, size, SPINNER_PADDING, SPINNER_PADDING));
        this.size = size;
        createSpinnerViews();
    }

    void createSpinnerViews() {
        SpinnerViews = new SpinnerView[size];

        for (int i = 0; i < size; i++) {
            SpinnerView sv = new SpinnerView();
            SpinnerViews[i] = sv;
            add(sv);
        }
    }

    // Set the displays from a card array.
    void setCards(Card[] cards) {
        for (int i = 0; i < SpinnerViews.length; i++) {
            SpinnerViews[i].setText(cards[i].toString());
        }
    }
}
