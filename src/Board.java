import java.util.ArrayList;

public class Board {

    private static final int[][] SHIFTS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int hamming = -1;
    private int manhattan;

    private int[] blocks;
    private int size;

    /**
     * construct a board from an n-by-n array of blocks
     * where blocks[i][j] = block in row i, column j
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        size = blocks.length;
        this.blocks = new int[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.blocks[i * size + j] = blocks[i][j];
            }
        }
        //hamming = calcHamming();
        manhattan = calcManhattan();
    }

    private Board(int[] blocks, int size) {
        this.blocks = blocks;
        this.size = size;
        //hamming = calcHamming();
        manhattan = calcManhattan();
    }

    /**
     * board dimension n
     *
     * @return
     */
    public int dimension() {
        return size;
    }

    /**
     * number of blocks out of place
     *
     * @return
     */
    public int hamming() {
        if (hamming == -1)
            hamming = calcHamming();
        return hamming;
    }

    private int calcHamming() {
        int hamming = 0;

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != 0 && blocks[i] != i+1) {
                hamming++;
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
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != 0) {
                int dy = Math.abs(i / size - (blocks[i]-1) / size);
                int dx = Math.abs(i % size - (blocks[i]-1) % size);
                manhattan += dx + dy;
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
        return manhattan == 0;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
        if (blocks[0] != 0 && blocks[1] != 0) {
            return swap(0,0,1,0);
        } else {
            return swap(0,1,1,1);
        }
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
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                row = i / size;
                col = i % size;
            }
        }

        ArrayList<Board> boards = new ArrayList<>();
        for (int i = 0; i < SHIFTS.length; i++) {
            Board board = swap(col, row, col + SHIFTS[i][0], row + SHIFTS[i][1]);
            if (board != null) {
                boards.add(board);
            }
        }

        return boards;
    }

    private Board swap(int x1, int y1, int x2, int y2) {
        if (!isInBounds(x1) || !isInBounds(y1) || !isInBounds(x2) || !isInBounds(y2)) return null;

        int[] b = createCopyOfBlocks();
        int t = b[y1 * size + x1];
        b[y1 * size + x1] = b[y2 * size + x2];
        b[y2 * size + x2] = t;

        Board board = new Board(b, size);
        return board;
    }

    private boolean isInBounds(int coordinate) {
        return coordinate < size && coordinate >= 0;
    }

    private int[] createCopyOfBlocks() {
        int[] b = new int[size * size];
        System.arraycopy(blocks, 0, b, 0, blocks.length);
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
        if (other == null) return false;
        if (!(other instanceof Board)) return false;

        Board otherBoard = (Board) other;
        if (otherBoard.size != size) return false;

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != otherBoard.blocks[i]) {
                return false;
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
        sb.append(size + "\n");
        for (int i = 0; i < blocks.length; i++) {
            sb.append(blocks[i]);
            sb.append(" ");
            if (i % size == size-1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}