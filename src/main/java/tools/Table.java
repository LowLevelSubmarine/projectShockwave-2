package tools;

import java.util.LinkedList;

public class Table {
    private int[] sizes;
    private String[] titels;
    private Enum[] alignments;
    private LinkedList<String[]> rows = new LinkedList<>();

    public void setSizes(int... sizes) {
        this.sizes = sizes;
    }
    public void setAlignments(ALIGN... alignments) {
        this.alignments = alignments;
    }
    public void setTitles(String... titles) {
        this.titels = titles;
    }
    public void addRow(String... row) {
        rows.add(row);
    }

    @Override
    public String toString() {
        String main = "";
        this.rows.addFirst(this.titels);
        int rowLength = rows.size() + 3;
        int columnLength = (rows.get(0).length * 2) + 1;

        for(int row = 0; row < rowLength; row++) {
            int tableRow = getTableRow(row);
            if (row != 0) {
                main += "\n";
            }
            for (int column = 0; column < columnLength; column++) {
                String insert;
                int tableColumn = getTableColumn(column);
                if ((column % 2 == 0) && (row == 0 || row == 2 || row == rowLength - 1)) {
                    insert = "+";
                } else if ((column % 2 != 0) && (row == 0 || row == 2 || row == rowLength - 1)) {
                    insert = "-" + Toolkit.spacer(sizes[tableColumn] , '-') + "-";
                } else if ((column % 2 == 0) && !(row == 0 || row == 2 || row == rowLength - 1)) {
                    insert = "|";
                } else {
                    Enum realAlign;
                    if (tableRow == 0) {
                        realAlign = ALIGN.LEFT;
                    } else {
                        realAlign = alignments[tableColumn];
                    }
                    insert = " " + Toolkit.align(rows.get(tableRow)[tableColumn], ' ', sizes[tableColumn], realAlign) + " ";
                }
                main += insert;
            }
        }
        return main;
    }

    private int getTableColumn(int column) {
        return (column - 1) / 2;
    }

    private int getTableRow(int row) {
        if (row == 1) {
            return 0;
        } else {
            return row - 2;
        }
    }

    public enum ALIGN {
        LEFT, RIGHT, CENTER,
    }
}
