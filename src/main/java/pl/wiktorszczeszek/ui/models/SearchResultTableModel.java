package pl.wiktorszczeszek.ui.models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SearchResultTableModel extends AbstractTableModel {
    private final String[] columnNames = {"W nazwie", "W treści", "Plik"};
    private List<SearchResultRow> rows = new ArrayList<>();

    public void setRows(List<SearchResultRow> rows) {
        this.rows = rows;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows != null ? rows.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < columnNames.length) {
            return columnNames[columnIndex];
        } else {
            return super.getColumnName(columnIndex);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rows == null || rowIndex < 0 || rowIndex >= rows.size()) {
            return null;
        }

        SearchResultRow row = rows.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> row.isFileNameOccurrence();
            case 1 -> row.textOccurrenceCount();
            case 2 -> row.file();
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) return String.class;
        return Object.class;
    }
}