package helpers;

import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

public class CustomJTable extends javax.swing.JTable implements TableModelListener, Scrollable,
        TableColumnModelListener, ListSelectionListener, CellEditorListener,
        Accessible, RowSorterListener {
    public CustomJTable(DefaultTableModel tableModel) {
        super(tableModel);
        tableModel.addTableModelListener(this);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 2;
    }
}
