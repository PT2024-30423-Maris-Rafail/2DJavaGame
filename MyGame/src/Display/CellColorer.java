package Display;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Allows to paint a certain row of a JTable
 */
public class CellColorer extends DefaultTableCellRenderer {
    private final String targetString;
    private final Color highlightColor;

    public CellColorer(String targetString, Color highlightColor) {
        this.targetString = targetString;
        this.highlightColor = highlightColor;
    }

    //Java loves you! Until you need this type of stuff for... coloring a row...
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Check if the row contains the specific string in any cell
        //in the call, this will check if the user is in the top 10 and highlight their scores
        boolean containsString = false;
        for (int col = 0; col < table.getColumnCount(); col++) {
            Object cellValue = table.getValueAt(row, col);
            if (cellValue != null && cellValue.toString().contains(targetString)) {
                containsString = true;
                break;
            }
        }
        //we color if the string is detected
        if (containsString) {
            c.setBackground(highlightColor);
        } else {
            c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        }

        return c;
    }
}



