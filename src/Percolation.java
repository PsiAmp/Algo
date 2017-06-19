import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int[][] shift = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int FULL = 0;
    private static final int OPEN = 1;
    private int[][] data;
    private int openPoints = 0;
    private WeightedQuickUnionUF union;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        data = new int[n][n];
        union = new WeightedQuickUnionUF(n*n);
    }

    private boolean indexesOutOfBounds(int row, int col) {
        return row <= 0 || col <= 0 || row > data.length || col > data.length;
    }

    private void validate(int row, int col) {
        if (indexesOutOfBounds(row, col)) throw new IndexOutOfBoundsException();
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            data[row-1][col-1] = OPEN;
            openPoints++;
            for (int i = 0; i < shift.length; i++) {
                int shiftRow = row + shift[i][0];
                int shiftColumn = col + shift[i][1];
                if (!indexesOutOfBounds(shiftRow, shiftColumn) && isOpen(shiftRow, shiftColumn))
                    union.union(getIndex(row, col), getIndex(shiftRow, shiftColumn));
            }
        }
    }

    private int getIndex(int row, int col) {
        return (row-1) * data.length + col - 1;
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return data[row-1][col-1] == OPEN;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return data[row-1][col-1] == FULL;
    }

    public int numberOfOpenSites() {
        return openPoints;
    }

    public boolean percolates() {
        for (int i = 1; i <= data.length; i++) {
            for (int j = 1; j <= data.length; j++) {
                if (union.connected(getIndex(1, i), getIndex(data.length, j))) {
                    return true;
                }
            }
        }
        return false;
    }
}