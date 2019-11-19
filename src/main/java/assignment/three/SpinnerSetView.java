package assignment.three;

import java.awt.GridLayout;
import javax.swing.*;

public class SpinnerSetView extends JPanel {
    private static final int PADDING = 10;
    int size;
    CardView[] cardViews;

    SpinnerSetView(int size) {
        setLayout(new GridLayout(1, size, PADDING, PADDING));
        this.size = size;
        createCardViews();
    }

    void createCardViews() {
        cardViews = new CardView[size];

        for (int i = 0; i < size; i++) {
            CardView cv = new CardView();
            cardViews[i] = cv;
            add(cv);
        }
    }

    void setCardTexts(String[] texts) {
        for (int i = 0; i < cardViews.length; i++) {
            cardViews[i].setText(texts[i]);
        }
    }
}
