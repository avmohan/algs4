package io.github.avmohan.coursera.algs4;

import static edu.princeton.cs.algs4.StdOut.println;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by avmohan on 16/10/17.
 */
public class Percolation {

    // Encodes 3 values.
    // 0th bit of every byte indicates whether the positionis open or closed
    // 1st bit indicates if it's connected to top. (only relevant if it's a root)
    // 2nd bit indicates if it's connected to bot. (only relevant if it's a root)
    private byte flags[];

    // get k-th bit from right in flag[i]
    private boolean getBit(int i, int k) {
        return ((flags[i] >> k) & 1) == 1;
    }

    // set k-th bit from right in flag[i]
    private void setBit(int i, int k) {
        flags[i] |= 1 << k;
    }

    // Oth bit in flags used for grid
    private boolean grid(int row, int col) {
        return getBit(getNode(row, col), 0);
    }

    private void setGrid(int row, int col) {
        setBit(getNode(row, col), 0);
    }

    // 1st bit in flags used for topConnected
    private boolean topConnected(int node) {
        return getBit(node, 1);
    }

    private void setTopConnected(int node) {
        setBit(node, 1);
    }

    // 2nd bit in flags used for botConnected
    private boolean botConnected(int node) {
        return getBit(node, 2);
    }

    private void setBotConnected(int node) {
        setBit(node, 2);
    }

    // size of grid
    private final int n;

    private int numberOfOpenSites;

    // Will contain a node for each element in the grid.
    private final WeightedQuickUnionUF mainUf;

    // flag to indicate if system percolates.
    private boolean percolates;

    // Offsets for the 4 neighbours - top, right, bot, left
    private static final int[] dRow = {-1, 0, 1, 0};
    private static final int[] dCol = {0, 1, 0, -1};

    // Create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size should be greater than zero.");
        }
        this.n = n;

        this.mainUf = new WeightedQuickUnionUF(n * n);
        this.flags = new byte[n * n];
    }

    // Open site (row, col) if it is not open already
    public void open(int row, int col) {
        validateBounds(row, col);
        if (isOpen(row, col)) {
            return;
        }

        // Open the site in grid.
        setGrid(row, col);
        numberOfOpenSites++;

        // Find root node of current cell
        // Root will always be the root node of the component containing the cell being opened.
        int root = mainUf.find(getNode(row, col));

        // If a top row cell is being opened, set topConnected to true.
        if (row == 1) {
            setTopConnected(root);
        }

        // If a bot row cell is being opened, set botConnected to true.
        if (row == n) {
            setBotConnected(root);
        }

        // If any of the neighbours are open, connect the corresponding nodes.
        for (int i = 0; i < 4; i++) {
            int neighbourRow = row + dRow[i];
            int neighbourCol = col + dCol[i];
            if (isValid(neighbourRow, neighbourCol) && isOpen(neighbourRow, neighbourCol)) {
                // Find the root of the neighbour
                int neighbourRoot = mainUf.find(getNode(neighbourRow, neighbourCol));

                // Merge the 2 components (Better use roots for merging as we have them anyway)
                mainUf.union(root, neighbourRoot);

                // Update topConnected & botConnected - if either is connected, the new component will
                // also be connected.
                int newRoot = mainUf.find(getNode(row, col));
                if (topConnected(root) || topConnected(neighbourRoot)) {
                    setTopConnected(newRoot);
                }
                if (botConnected(root) || botConnected(neighbourRoot)) {
                    setBotConnected(newRoot);
                }
                root = newRoot;
            }
        }
        if (topConnected(root) && botConnected(root)) {
            percolates = true;
        }
    }

    // Is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateBounds(row, col);
        return grid(row, col);
    }

    // Is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateBounds(row, col);
        return topConnected(mainUf.find(getNode(row, col)));
    }

    // Number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // Does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    private boolean isValid(int row, int col) {
        return (row >= 1 && row <= n && col >= 1 && col <= n);
    }

    private void validateBounds(int row, int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException("Row or column not within bounds");
        }
    }

    // Get the union-find structure node for a given cell.
    private int getNode(int row, int col) {
        validateBounds(row, col);
        return (row - 1) * n + col - 1;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        println(p.percolates());
        p.open(1, 1);
        p.open(2, 1);
        println(p.percolates());
        println(p.isFull(2, 1));

        p = new Percolation(1);
        println(p.percolates());
        p.open(1, 1);
        println(p.percolates());

        p = new Percolation(3);
        p.open(1, 1);
        p.open(3, 1);
        println(p.percolates()); // false
        println(p.isFull(1, 1)); // true
        println(p.isFull(3, 1)); // false
        p.open(1, 3);
        p.open(2, 3);
        println(p.percolates()); // false
        p.open(3, 3);
        println(p.percolates()); // true
        println(p.isFull(3, 1)); // false
    }

}
