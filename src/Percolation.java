import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int[][] SHIFT = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int OPEN = 1;
    private int[][] data;
    private int openPoints = 0;
    private WeightedQuickUnionUF union;
    private WeightedQuickUnionUF fullUnion;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        data = new int[n][n];
        union = new WeightedQuickUnionUF(n * n + 2);
        fullUnion = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 0; i < n; i++) {
            union.union(i, n * n);
            fullUnion.union(i, n * n);
            union.union(n * n - i - 1, n * n + 1);
        }
    }

    private boolean indexesOutOfBounds(int row, int col) {
        return row <= 0 || col <= 0 || row > data.length || col > data.length;
    }

    private void validate(int row, int col) {
        if (indexesOutOfBounds(row, col)) throw new IndexOutOfBoundsException("row = " + row + "; col = " + col);
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            data[row-1][col-1] = OPEN;
            openPoints++;
            for (int i = 0; i < SHIFT.length; i++) {
                int shiftRow = row + SHIFT[i][0];
                int shiftColumn = col + SHIFT[i][1];
                if (!indexesOutOfBounds(shiftRow, shiftColumn) && isOpen(shiftRow, shiftColumn)) {
                    union.union(getIndex(row, col), getIndex(shiftRow, shiftColumn));
                    fullUnion.union(getIndex(row, col), getIndex(shiftRow, shiftColumn));
                }
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
        return isOpen(row, col) && fullUnion.connected(getIndex(row, col), data.length * data.length);
    }

    public int numberOfOpenSites() {
        return openPoints;
    }

    public boolean percolates() {
        if (data.length == 1) return numberOfOpenSites() > 0;
        return union.connected(data.length*data.length, data.length*data.length + 1);
    }
}