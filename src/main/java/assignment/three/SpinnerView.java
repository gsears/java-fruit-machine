package assignment.three;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

public class SpinnerView extends JPanel {
    JLabel cardText;

    public SpinnerView() {
        this("");
    }

    public SpinnerView(String initialText) {
        setLayout(new BorderLayout());
        setBackground(Color.YELLOW);

        // Create and Add Components
        createCardText();

        setText(initialText);
    }

    public void createCardText() {
        cardText = new JLabel();
        add(cardText, BorderLayout.CENTER);
    }

    public void setText(String text) {
        cardText.setText(text);
    }
}
