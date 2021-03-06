import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private class SearchNode {
        private Board board;
        private SearchNode parent;
        private int moves;
//        private int hamming;
        private final int manhattan;

        public SearchNode(Board board, SearchNode parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;

            manhattan = board.manhattan() + moves;
            //hamming = board.hamming() + moves;
        }
    }

    private class BoardComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            if (node1.manhattan > node2.manhattan) return 1;
            if (node1.manhattan < node2.manhattan) return -1;

//            if (node1.hamming > node2.hamming) return 1;
//            if (node1.hamming < node2.hamming) return -1;

            return 0;
        }
    }

    private List<Board> solution;

    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial
     */
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(new BoardComparator());
        pq.insert(new SearchNode(initial, null, 0));
        SearchNode node = pq.delMin();

        Board twin = initial.twin();
        pq.insert(new SearchNode(twin, null, 0));

        while (!node.board.isGoal()) {
            Iterable<Board> neighbors = node.board.neighbors();
            for (Board neighbor : neighbors) {
                // Critical optimization: don't include the same board of the previous node

                /**
                 * Grader still says there's an issue:
                 * equals() compares a board to a board that is not a neighbor of a neighbor
                 * this suggests a bug in the critical optimization
                 */
                if (node.parent == null || !neighbor.equals(node.parent.board)) {
                    pq.insert(new SearchNode(neighbor, node, node.moves + 1));
                }
            }
            node = pq.delMin();
        }

        solution = new ArrayList<Board>();
        while (node != null) {
            solution.add(node.board);
            node = node.parent;
        }
        Collections.reverse(solution);

        // No solution
        if (solution.get(0).equals(twin)) {
            solution = null;
        }
    }

    /**
     * is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
        return solution != null;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves() {
        return solution == null ? -1 : solution.size() - 1;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     * @return
     */
    public Iterable<Board> solution() {
        return solution;
    }

    /**
     * solve a slider puzzle (given below)
     * @param args
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In("D:\\Projects\\Algo\\src\\8puzzle\\puzzle00.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}