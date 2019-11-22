package assignment.three;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * SpinnerView.java | Gareth Sears - 2493194
 * 
 * Encapsulates the 'card' view, so many cards can be made easily.
 * 
 */

@SuppressWarnings("serial")
public class SpinnerView extends JPanel {

    JLabel cardTextLabel;

    public SpinnerView() {
        this(""); // Initialise with no text if none is provided.
    }

    public SpinnerView(String initialText) {

        setLayout(new GridBagLayout());
        setBackground(Color.YELLOW); // Let's burn some retinas.
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cardTextLabel = new JLabel();
        setText(initialText);

        // GridBagLayout used to center text nicely.
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
