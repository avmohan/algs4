package io.github.avmohan.coursera.algs4.part1.week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Board {

    // the grid and the dimension
    private final int[][] blocks;
    private final short n;

    private Position zero;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this(blocks, true);
    }

    private Board(int[][] blocks, boolean copy) {
        assert blocks.length == blocks[0].length;
        this.n = (short) blocks.length;
        if (!copy) {
            this.blocks = blocks;
        } else {
            this.blocks = copy2d(blocks);
            findZeroPosition();
        }
    }

    private void findZeroPosition() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    zero = new Position(i, j);
                }
            }
        }
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // The goal row of block x
    private int goalRow(int x) {
        if (x == 0) return n - 1;
        return (x - 1) / n;
    }

    // The goal column of block x
    private int goalCol(int x) {
        if (x == 0) return n - 1;
        return (x - 1) % n;
    }

    // The block at position (i, j) in the goal board
    private int goalBlock(int row, int col) {
        if (row == n - 1 && col == n - 1) return 0;
        return n * row + col + 1;
    }

    // swap blocks at (row1, col1) and (row2, col2)
    private static void swap(int[][] blocks, int row1, int col1, int row2, int col2) {
        int temp = blocks[row1][col1];
        blocks[row1][col1] = blocks[row2][col2];
        blocks[row2][col2] = temp;
    }

    // make a copy of blocks[][]
    private static int[][] copy2d(int[][] blocks) {
        int[][] nBlocks = new int[blocks.length][];
        for (int i = 0; i < blocks.length; i++)
            nBlocks[i] = Arrays.copyOf(blocks[i], blocks[i].length);
        return nBlocks;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != goalBlock(i, j))
                    hamming++;
            }
        }
        return hamming;
    }


    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int x = blocks[i][j];
                if (x != 0)
                    manhattan += abs(goalRow(x) - i) + abs(goalCol(x) - j);
            }
        }
        return manhattan;
    }

    private int abs(int x) {
        if (x < 0) return -x;
        return x;
    }


    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != goalBlock(i, j))
                    return false;
            }
        }
        return true;
    }


    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] nBlocks = copy2d(blocks);
        if (nBlocks[0][0] != 0 && nBlocks[0][1] != 0) swap(nBlocks, 0, 0, 0, 1);
        else if (nBlocks[1][0] != 0 && nBlocks[1][1] != 0) swap(nBlocks, 1, 0, 1, 1);

        Board twin = new Board(nBlocks, false);
        twin.zero = this.zero;
        return twin;
    }

    // The spec in the assignment prohibits overriding hashcode so it's not consistent with equals.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return n == board.n && Arrays.deepEquals(blocks, board.blocks);
    }

    private enum Move {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        final int dRow;
        final int dCol;

        Move(int dRow, int dCol) {
            this.dRow = dRow;
            this.dCol = dCol;
        }
    }

    // Return the board after a particular move.
    private Board afterMove(Move m) {
        Position nZero = new Position(zero.row + m.dRow, zero.col + m.dCol);
        assert isValidPos(nZero.row, nZero.col);

        int[][] nBlocks = copy2d(blocks);
        swap(nBlocks, zero.row, zero.col, nZero.row, nZero.col);
        Board nBoard = new Board(nBlocks, false);
        nBoard.zero = nZero;
        return nBoard;
    }

    private boolean isValidPos(int i, int j) {
        return 0 <= i && i < n && 0 <= j && j < n;
    }

    private boolean isValidMove(Move m) {
        return isValidPos(zero.row + m.dRow, zero.col + m.dCol);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbours = new ArrayList<>();
        for (Move move : Move.values()) {
            if (isValidMove(move)) {
                neighbours.add(afterMove(move));
            }
        }
        return neighbours;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    private static class Position {
        final short row;
        final short col;

        Position(int row, int col) {
            this.row = (short) row;
            this.col = (short) col;
        }

    }
}
