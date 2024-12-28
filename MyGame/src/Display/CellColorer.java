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
        //if we have the string as username, we color
        Object cellValue = table.getValueAt(row, 1);
        if (cellValue != null && cellValue.toString().equals(targetString)) {
            c.setBackground(highlightColor);
        } else {
            c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        }

        return c;
    }
}





