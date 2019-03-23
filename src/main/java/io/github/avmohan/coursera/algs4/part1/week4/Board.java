package io.github.avmohan.coursera.algs4.part1.week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Board {

    // the grid and the dimension
    private final short[][] blocks;
    private final short n;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        assert blocks.length == blocks[0].length;
        this.n = (short) blocks.length;
        this.blocks = new short[n][n];
        for (int i = 0; i < n; i++) {
            this.blocks[i] = new short[n];
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = (short) blocks[i][j];
            }
        }
    }

    private Board(short[][] blocks) {
        assert blocks.length == blocks[0].length;
        this.n = (short) blocks.length;
        this.blocks = new short[n][n];
        for (int i = 0; i < n; i++) {
            this.blocks[i] = new short[n];
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, n);
        }
    }

    private Position findZeroPosition() {
        Position zero = null;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    zero = new Position(i, j);
                }
            }
        }
        if (zero == null) throw new IllegalStateException("No zero in board");
        return zero;
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

    // Swaps blocks at pos1 & pos2
    private void swap(Position pos1, Position pos2) {
        short temp = blocks[pos1.row][pos1.col];
        blocks[pos1.row][pos1.col] = blocks[pos2.row][pos2.col];
        blocks[pos2.row][pos2.col] = temp;
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
        Board twin = new Board(blocks);
        short[][] nBlocks = twin.blocks;
        if (nBlocks[0][0] != 0 && nBlocks[0][1] != 0) twin.swap(new Position(0, 0), new Position(0, 1));
        else if (nBlocks[1][0] != 0 && nBlocks[1][1] != 0) twin.swap(new Position(1, 0), new Position(1, 1));
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
    private Board afterMove(Move m, Position zero) {
        Position nZero = new Position(zero.row + m.dRow, zero.col + m.dCol);
        assert isValidPos(nZero);
        Board nBoard = new Board(blocks);
        nBoard.swap(zero, nZero);
        return nBoard;
    }

    private boolean isValidPos(Position p) {
        return 0 <= p.row && p.row < n && 0 <= p.col && p.col < n;
    }

    private boolean isValidMove(Move m, Position zero) {
        return isValidPos(zero.move(m));
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbours = new ArrayList<>();
        Position zero = findZeroPosition();
        for (Move move : Move.values()) {
            if (isValidMove(move, zero)) {
                neighbours.add(afterMove(move, zero));
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

        Position move(Move m) {
            return new Position(row + m.dRow, col + m.dCol);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return row == position.row &&
                col == position.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
}
