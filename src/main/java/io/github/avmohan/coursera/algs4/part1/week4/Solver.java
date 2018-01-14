package io.github.avmohan.coursera.algs4.part1.week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private final int numMoves;
    private final Iterable<Board> solution;

    /**
     * A node of the search tree
     */
    private static class Node implements Comparable<Node> {
        final Board board;
        final int moves;
        final Node prev;

        Node(Board board, Node prev) {
            this.board = board;
            this.prev = prev;
            this.moves = (prev != null) ? prev.moves + 1 : 0;
        }

        Node(Board board) {
            this(board, null);
        }

        int manhattan() {
            return moves + board.manhattan();
        }

        int hamming() {
            return moves + board.hamming();
        }

        List<Node> nextNodes() {
            List<Node> nodes = new ArrayList<>();
            for (Board neighbour : board.neighbors()) {
                if (prev != null && neighbour.equals(prev.board))
                    continue;
                nodes.add(new Node(neighbour, this));
            }
            return nodes;
        }

        // The spec in the assignment prohibits overriding equals so it's not consistent with compareTo.
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.manhattan(), other.manhattan());
        }

        boolean isGoal() {
            return board.isGoal();
        }

        // Get steps taken to reach from initial node to this node
        Iterable<Board> steps() {
            Stack<Board> solution = new Stack<>();
            Node cur = this;
            while (cur != null) {
                solution.push(cur.board);
                cur = cur.prev;
            }
            return solution;
        }
    }


    // find steps to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("The initial board cannot be null");
        // Add initial node to a min-pq, mainPQ
        // Add initial node's twin to another min-pq, twinPQ
        // While true:
        //      Pop node off mainPQ and check if it's goal node - if yes, exit with soln. Else add all neighbour nodes to mainPQ
        //      Pop node off twinPQ and check if it's goal node - if yes, exit with unsolvable. Else add all neighbour nodes to twinPQ

        MinPQ<Node> mainPQ = new MinPQ<>();
        mainPQ.insert(new Node(initial));

        MinPQ<Node> twinPQ = new MinPQ<>();
        twinPQ.insert(new Node(initial.twin()));

        while (true) {
            Node main = mainPQ.delMin();
            Node twin = twinPQ.delMin();
            if (main.isGoal()) {
                this.numMoves = main.moves;
                this.solution = main.steps();
                break;
            } else if (twin.isGoal()) {
                this.numMoves = -1;
                this.solution = null;
                break;
            } else {
                main.nextNodes().forEach(mainPQ::insert);
                twin.nextNodes().forEach(twinPQ::insert);
            }
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return (solution != null);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return numMoves;
    }

    // sequence of boards in a shortest steps; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
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
