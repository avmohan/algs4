import static edu.princeton.cs.algs4.StdOut.println;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by avmohan on 16/10/17.
 */
public class Percolation {

  // 0 means blocked site, 1 means open site.
  private final int[][] grid;

  // size of grid
  private final int n;

  private int numberOfOpenSites;

  // Size N*N + 2
  // Has a joining point for top and bottom rows.
  // top will be 0, grid will be 1 to N*N, bot will be N*N+1
  // This is for the percolates query
  private final WeightedQuickUnionUF mainUf;

  // Size N*N + 1
  // Has a joining point for top rows only.
  // top will be 0, grid will be 1 to N*N
  // This is for the isFull query (to avoid back-wash)
  private final WeightedQuickUnionUF auxUf;

  // Aliases for top & bottom join nodes in the union find structures.
  private final int topNode; // 0
  private final int botNode; // N*N+1

  // For the 4 neighbours - top, right, bot, left
  private static final int[] dRow = {-1, 0, 1, 0};
  private static final int[] dCol = {0, 1, 0, -1};


  // Create n-by-n grid, with all sites blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Grid size should be greater than zero.");
    }
    this.n = n;
    this.mainUf = new WeightedQuickUnionUF(n * n + 2);
    this.auxUf = new WeightedQuickUnionUF(n * n + 1);
    this.grid = new int[n + 1][n + 1]; // just to use 1-based indexing.

    // aliases for the top & bot special nodes.
    this.topNode = 0;
    this.botNode = n * n + 1;
  }

  // Open site (row, col) if it is not open already
  public void open(int row, int col) {
    validateBounds(row, col);
    if (isOpen(row, col)) {
      return;
    }
    grid[row][col] = 1;
    numberOfOpenSites++;

    // If any of the neighbours are open, connect the corresponding nodes.
    for (int i = 0; i < 4; i++) {
      int nRow = row + dRow[i];
      int nCol = col + dCol[i];
      if (isValid(nRow, nCol) && isOpen(nRow, nCol)) {
        mainUf.union(getNode(row, col), getNode(nRow, nCol));
        auxUf.union(getNode(row, col), getNode(nRow, nCol));
      }
    }

    if (row == 1) {
      // if in top row, connect to special top node (both main & aux uf)
      mainUf.union(topNode, getNode(row, col));
      auxUf.union(topNode, getNode(row, col));
    }
    if (row == n) {
      // if in bot row, connect to special bot node (only main uf)
      mainUf.union(botNode, getNode(row, col));
    }
  }

  // Is site (row, col) open?
  public boolean isOpen(int row, int col) {
    validateBounds(row, col);
    return grid[row][col] == 1;
  }

  // Is site (row, col) full?
  public boolean isFull(int row, int col) {
    validateBounds(row, col);
    return auxUf.connected(topNode, getNode(row, col));
  }

  // Number of open sites
  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }

  // Does the system percolate?
  public boolean percolates() {
    return mainUf.connected(topNode, botNode);
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
    return (row - 1) * n + col;
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
  }

}
