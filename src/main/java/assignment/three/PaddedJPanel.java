package assignment.three;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class PaddedJPanel extends JPanel {

    // Blank constructor allows for padding to be changed later
    public PaddedJPanel() {
        super();
    }

    public PaddedJPanel(int padding) {
        super();
        setPadding(padding);
    }

    public void setPadding(int padding) {
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
    }
}
