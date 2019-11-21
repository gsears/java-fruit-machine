package assignment.three;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SpinnerView extends JPanel {

    JLabel cardTextLabel;

    public SpinnerView() {
        this("");
    }

    public SpinnerView(String initialText) {
        setLayout(new GridBagLayout());
        setBackground(Color.YELLOW);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
