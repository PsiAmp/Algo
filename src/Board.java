import java.util.Random;

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
        hamming = calcHamming();
        manhattan = calcManhattan();
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
        return hamming;
    }

    private int calcHamming() {
        int hamming = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != 1 + i * dimension() + j) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * @return
     */
    public int manhattan() {
        return manhattan;
    }

    private int calcManhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0) {
                    int dy = Math.abs((blocks[i][j] - 1) / dimension() - i);
                    int dx = Math.abs((blocks[i][j] - 1) % dimension() - j);
                    manhattan += dx + dy;
                }
            }
        }
        return manhattan;
    }

    /**
     * is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        // TODO measure speed division vs multiplication
//        for (int i = 0; i < dimension() * dimension() - 1; i++) {
//            if (blocks[i/dimension()][i%dimension()] != i+1) {
//                return false;
//            }
//        }
//        return true;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 1 + i * dimension() + j && blocks[i][j] != 0) {
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
        // TODO fix this: doesn't return a twin
        int[][] twin = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                twin[i][j] = blocks[i][j];
            }
        }

        Random r = new Random();
        int val1 = r.nextInt(9) + 1;
        int val2 = (r.nextInt(8) + 1 + 1 + val1) % dimension();

        return swap(val1 / dimension(), val1 % dimension(), val2 / dimension(), val2 % dimension());
    }

    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        // Search empty item coordinates
        int row = 0;
        int col = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }

        ArrayList<Board> boards = new ArrayList<>();
        for (int i = 0; i < SHIFTS.length; i++) {
            Board board = swap(row, col, row + SHIFTS[i][0], col + SHIFTS[i][1]);
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

        Board board = new Board(b);
        return board;
    }

    private boolean isInBounds(int coordinate) {
        return coordinate < dimension() && coordinate >= 0;
    }

    private int[][] createCopyOfBlocks() {
        int[][] b = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                b[i][j] = blocks[i][j];
            }
        }
        return b;
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

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (otherBoard.blocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * string representation of this board (in the output format specified below)
     *
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                sb.append(blocks[i][j]);
                if (j + 1 < dimension()) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}