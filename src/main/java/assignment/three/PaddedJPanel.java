package assignment.three;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * PaddedJPanel.java | Gareth Sears - 2493194
 * 
 * A subclass of JPanel that makes it easier to set padding all around the component using an empty
 * border of a specified size (feels like a clean implementation...)
 */

@SuppressWarnings("serial")
class PaddedJPanel extends JPanel {

    int padding;

    // Blank constructor allows for padding to be changed later
    public PaddedJPanel() {
    }

    public PaddedJPanel(int padding) {
        this.padding = padding;
        setPadding(padding);
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
    }
}
