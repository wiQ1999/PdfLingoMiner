package pl.wiktorszczeszek.ui.models;

import pl.wiktorszczeszek.core.domain.SearchResult;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SearchResultTableModel extends AbstractTableModel {
    private final String[] columnNames = {"W treści", "Plik"};
    private List<SearchResult> results = new ArrayList<>();

    public void setResults(List<SearchResult> results) {
        this.results = results;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return results != null ? results.size() : 0;
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
        if (results == null || rowIndex < 0 || rowIndex >= results.size()) {
            return null;
        }

        SearchResult result = results.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> result.getOccurrenceCount();
            case 1 -> result.getFile().getPath();
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) return String.class;
        return Object.class;
    }
}