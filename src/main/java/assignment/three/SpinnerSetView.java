package assignment.three;

import java.awt.GridLayout;
import javax.swing.*;

public class SpinnerSetView extends JPanel {
    private static final int PADDING = 10;
    int size;
    SpinnerView[] SpinnerViews;

    SpinnerSetView(int size) {
        setLayout(new GridLayout(1, size, PADDING, PADDING));
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
