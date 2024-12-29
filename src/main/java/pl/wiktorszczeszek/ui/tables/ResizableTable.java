package pl.wiktorszczeszek.ui.tables;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.Component;

public class ResizableTable extends JTable {
    private static final int margin = 10;
    private int currentColumnIndex;
    private TableColumn currentColumn;

    public ResizableTable(TableModel dm){
        super(dm);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public void adjustColumnsWidthToHeaders() {
        for (currentColumnIndex = 0; currentColumnIndex < getColumnCount(); currentColumnIndex++) {
            currentColumn = getColumnModel().getColumn(currentColumnIndex);

            int width = getHeaderWidth();

            width += margin;
            currentColumn.setPreferredWidth(width);
        }
    }

    public void adjustColumnWidth(int columnIndex) {
        currentColumnIndex = columnIndex;
        currentColumn = getColumnModel().getColumn(columnIndex);

        int headerWidth = getHeaderWidth();
        int cellWidth = getMaxCellWidth();
        int width = Math.max(headerWidth, cellWidth);

        width += margin;
        currentColumn.setPreferredWidth(width);
    }

    private int getHeaderWidth() {
        TableCellRenderer renderer = getTableHeader().getDefaultRenderer();
        Object value = currentColumn.getHeaderValue();
        Component component = renderer.getTableCellRendererComponent(
                this, value, false, false, 0, currentColumnIndex);

        return component.getPreferredSize().width;
    }

    private int getMaxCellWidth() {
        int maxWidth = 0;

        for (int row = 0; row < getRowCount(); row++) {
            TableCellRenderer renderer = getCellRenderer(row, currentColumnIndex);
            Object value = getValueAt(row, currentColumnIndex);
            Component component = renderer.getTableCellRendererComponent(
                    this, value, false, false, row, currentColumnIndex);
            int width = component.getPreferredSize().width;
            maxWidth = Math.max(maxWidth, width);
        }

        return maxWidth;
    }
}
