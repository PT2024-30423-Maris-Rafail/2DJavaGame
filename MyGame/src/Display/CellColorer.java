package Display;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;// Custom renderer to color rows

public class CellColorer extends DefaultTableCellRenderer {
    private final String targetString;
    private final Color highlightColor;

    public CellColorer(String targetString, Color highlightColor) {
        this.targetString = targetString;
        this.highlightColor = highlightColor;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        // Get the default renderer component
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Check if the row contains the specific string in any cell
        boolean containsTarget = false;
        for (int col = 0; col < table.getColumnCount(); col++) {
            Object cellValue = table.getValueAt(row, col);
            if (cellValue != null && cellValue.toString().contains(targetString)) {
                containsTarget = true;
                break;
            }
        }

        if (containsTarget) {
            c.setBackground(highlightColor);
        } else {
            c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        }

        return c;
    }
}



