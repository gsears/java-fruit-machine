package assignment.three;

import java.awt.GridLayout;

@SuppressWarnings("serial")
public class SpinnerSetView extends PaddedJPanel {
    private static final int SPINNER_PADDING = 10;
    int size;
    SpinnerView[] SpinnerViews;

    SpinnerSetView(int size) {
        setLayout(new GridLayout(1, size, SPINNER_PADDING, SPINNER_PADDING));
        this.size = size;
        createSpinnerViews();
    }

    void createSpinnerViews() {
        SpinnerViews = new SpinnerView[size];

        for (int i = 0; i < size; i++) {
            SpinnerView cv = new SpinnerView();
            SpinnerViews[i] = cv;
            add(cv);
        }
    }

    void setCards(Card[] cards) {
        for (int i = 0; i < SpinnerViews.length; i++) {
            SpinnerViews[i].setText(cards[i].toString());
        }
    }
}
