package Display;

import geoGame.ScoreBoard;
import geoGame.ScoreBoardRow;

import javax.swing.table.AbstractTableModel;

public class ScoreTable extends AbstractTableModel {
    private final String[] columnNames = {"Place", "Username", "Score"};
    private final ScoreBoard scoreBoard;

    public ScoreTable(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    @Override
    public int getRowCount() {
        return scoreBoard.scoreBoard.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ScoreBoardRow row = scoreBoard.scoreBoard[rowIndex];
        return switch (columnIndex) {
            case 0 -> row.getPlace();
            case 1 -> row.getUsername(); // Assuming 'time' is used as the score
            case 2 -> row.getTime();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
}
