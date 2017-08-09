import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {

    private static final int[][] SHIFTS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private final int hamming;
    private final int manhattan;

    private int[][] blocks;

    /**
     * construct a board from an n-by-n array of blocks
     * where blocks[i][j] = block in row i, column j
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        this.blocks = blocks;
        // TODO use cached values in priority functions
        hamming = hamming();
        manhattan = manhattan();
    }

    /**
     * board dimension n
     *
     * @return
     */
    public int dimension() {
        return blocks.length;
    }

    /**
     * number of blocks out of place
     *
     * @return
     */
    public int hamming() {

    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * @return
     */
    public int manhattan() {
    }

    /**
     * is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != 1 + i * dimension() + j) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
    }

    /**
     * does this board equal other?
     *
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Board)) return false;

        Board otherBoard = (Board) other;
        if (otherBoard.dimension() != dimension()) return false;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (otherBoard.blocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        // Empty item
        int row = 0;
        int col = 0;
        for (int i = 1; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }

        ArrayList<Board> boards = new ArrayList<>();
        for (int i = 0; i < SHIFTS.length; i++) {
            Board board = swap(row, col, SHIFTS[i][0], SHIFTS[i][1]);
            if (board != null) {
                boards.add(board);
            }
        }
        return boards;
    }

    private Board swap(int x1, int y1, int x2, int y2) {
        if (!isInBounds(x1) || !isInBounds(y1) || !isInBounds(x2) || !isInBounds(y2)) return null;

        int[][] b = createCopyOfBlocks();
        int t = b[x1][y1];
        b[x1][y1] = b[x2][y2];
        b[x2][y2] = t;

        return new Board(b);
    }

    private boolean isInBounds(int coordinate) {
        return coordinate < dimension() && coordinate >= 0;
    }

    private int[][] createCopyOfBlocks() {
        int[][] b = new int[dimension()][dimension()];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                b[i][j] = blocks[i][j];
            }
        }
        return b;
    }

    /**
     * string representation of this board (in the output format specified below)
     *
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                sb.append(blocks[i][j]);
                if (j + 1 < blocks[i].length) {
                    sb.append(" ");
                }
            }
            sb.append("\\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}