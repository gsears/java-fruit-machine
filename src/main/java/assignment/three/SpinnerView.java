package assignment.three;

import java.awt.*;
import java.awt.Color;
import javax.swing.*;

public class SpinnerView extends JPanel {
    JLabel cardTextLabel;

    public SpinnerView() {
        this("");
    }

    public SpinnerView(String initialText) {
        setLayout(new GridBagLayout());
        setBackground(Color.YELLOW);

        cardTextLabel = new JLabel();
        setText(initialText);

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.CENTER;

        add(cardTextLabel, gc);
    }


    public void setText(String text) {
        cardTextLabel.setText(text);
    }
}
