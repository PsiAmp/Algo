import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int[][] shift = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int FULL = 0;
    public static final int OPEN = 1;
    private int[][] data;
    private int openPoints = 0;
    private WeightedQuickUnionUF union;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        data = new int[n][n];
        union = new WeightedQuickUnionUF(data.length);
    }

    private void validate(int row, int col) {
        row--;
        col--;
        if (row < 0 || col < 0 || row > data.length-1 || col > data.length-1) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        row--;
        col--;
        if (data[row][col] != OPEN) {
            data[row][col] = OPEN;
            openPoints++;
            for (int i = 0; i < shift.length; i++) {
                int shiftRow = shift[i][0];
                int shiftColumn = shift[i][1];
                union();
            }
            union(index, index - 1);
            union(index, index + 1);
            union(index, index - dimension);
            union(index, index + dimension);
        }
    }

    private int getIndex(int row, int col) {
        return row
    }

    private void union(int a, int b) {
        if (b >= 0 && b < data.length) {
            union.union(a, b);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return data[row--][col--] == OPEN;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return data[row--][col--] == FULL;
    }

    public int numberOfOpenSites() {
        return openPoints;
    }

    public boolean percolates() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (union.connected(i, data.length - j - 1)) return true;
            }
        }
        return false;
    }
}