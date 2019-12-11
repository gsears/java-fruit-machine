package assignment.three;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * SpinnerView.java | Gareth Sears - 2493194
 * 
 * A JPanel which encapsulates the card view.
 */

@SuppressWarnings("serial")
public class SpinnerView extends JPanel {

    private JLabel cardTextLabel;

    // CONSTRUCTOR
    public SpinnerView() {
        this(""); // Initialise with no text if none is provided.
    }

    // CONSTRUCTOR
    public SpinnerView(String initialText) {

        setLayout(new GridBagLayout()); // Gridbag used to centralise text.
        setBackground(Color.YELLOW); // Let's burn some retinas.
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cardTextLabel = new JLabel();
        cardTextLabel.setText(initialText);

        add(cardTextLabel);
    }

    public void setText(String text) {
        cardTextLabel.setText(text);
    }
}
