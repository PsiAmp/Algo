import edu.princeton.cs.algs4.StdOut;

public class Board {

    private int[][] blocks;

    /**
     * construct a board from an n-by-n array of blocks
     * where blocks[i][j] = block in row i, column j
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        this.blocks = blocks;
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