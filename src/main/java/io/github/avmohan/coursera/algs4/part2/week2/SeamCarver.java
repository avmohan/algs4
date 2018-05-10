package io.github.avmohan.coursera.algs4.part2.week2;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("picture must be non-null");
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new IllegalArgumentException("Invalid coordinates");
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1)
            return 1000;
        return Math.sqrt(squaredDistance(picture.get(x - 1, y), picture.get(x + 1, y))
                + squaredDistance(picture.get(x, y - 1), picture.get(x, y + 1)));
    }

    private static double squaredDistance(Color c1, Color c2) {
        int red = c1.getRed() - c2.getRed();
        int green = c1.getGreen() - c2.getGreen();
        int blue = c1.getBlue() - c2.getBlue();
        return red * red + green * green + blue * blue;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // initialize matrices
        double[][] energy = new double[height()][width()];
        double[][] dist = new double[height()][width()];
        int[][] fromY = new int[height()][width()];
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energy[y][x] = energy(x, y);
                fromY[y][x] = -1;
                if (x == 0)
                    dist[y][x] = energy[y][x];
                else
                    dist[y][x] = Double.POSITIVE_INFINITY;
            }
        }

        // visit in topological order (LtoR for horizontal seam)
        int dys[] = {-1, 0, 1};
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                for (int dy : dys) {
                    if (isValid(x + 1, y + dy) &&
                            dist[y][x] + energy[y + dy][x + 1] < dist[y + dy][x + 1]) {
                        dist[y + dy][x + 1] = dist[y][x] + energy[y + dy][x + 1];
                        fromY[y + dy][x + 1] = y;
                    }
                }
            }
        }
        int[] seam = new int[width()];
        double minDist = Double.POSITIVE_INFINITY;
        for (int y = 0; y < height(); y++) {
            if (dist[y][width() - 1] < minDist) {
                minDist = dist[y][width() - 1];
                seam[width() - 1] = y;
            }
        }
        for (int x = width() - 2; x >= 0; x--) {
            seam[x] = fromY[seam[x + 1]][x];
        }
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        // for caching energy values
        double[][] energy = new double[height()][width()];

        // for maintaining dist to each pixel
        double[][] dist = new double[height()][width()];

        // for storing the column of the pixel in the previous row
        // from which this pixel is connected in the shortest path
        int[][] fromX = new int[height()][width()];

        // initialize everything
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energy[y][x] = energy(x, y);
                fromX[y][x] = -1;
                if (y == 0)
                    dist[y][x] = energy[y][x];
                else
                    dist[y][x] = Double.POSITIVE_INFINITY;
            }
        }

        // visit in topological order (TopToBot for vertical seam)
        int dxs[] = {-1, 0, 1};
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                for (int dx : dxs) {
                    if (isValid(x + dx, y + 1) &&
                            dist[y][x] + energy[y + 1][x + dx] < dist[y + 1][x + dx]) {
                        dist[y + 1][x + dx] = dist[y][x] + energy[y + 1][x + dx];
                        fromX[y + 1][x + dx] = x;
                    }
                }
            }
        }
        int[] seam = new int[height()];
        double minDist = Double.POSITIVE_INFINITY;

        // Find minimum dist pixel in last row. This is the
        // last pixel of the shortest TopToBot path.
        for (int x = 0; x < width(); x++) {
            if (dist[height() - 1][x] < minDist) {
                minDist = dist[height() - 1][x];
                seam[height() - 1] = x;
            }
        }

        // Follow fromX to get the pixels in the shortest
        // TopToBot path, and construct the vertical seam
        for (int y = height() - 2; y >= 0; y--) {
            seam[y] = fromX[y][seam[y + 1]];
        }
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, width(), height());
        Picture newPic = new Picture(picture.width(), picture.height() - 1);
        for (int row = 0; row < newPic.height(); row++) {
            for (int col = 0; col < newPic.width(); col++) {
                if (row < seam[col]) newPic.set(col, row, picture.get(col, row));
                else newPic.set(col, row, picture.get(col, row + 1));
            }
        }
        picture = newPic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam, height(), width());
        Picture newPic = new Picture(picture.width() - 1, picture.height());
        for (int row = 0; row < newPic.height(); row++) {
            for (int col = 0; col < newPic.width(); col++) {
                if (col < seam[row]) newPic.set(col, row, picture.get(col, row));
                else newPic.set(col, row, picture.get(col + 1, row));
            }
        }
        picture = newPic;
    }

    // along -> length of picture along the direction of the seam.
    // across -> length of picture across the direction of the seam.
    private static void validateSeam(int[] seam, int along, int across) {
        if (seam == null) throw new IllegalArgumentException("Seam must be non-null");
        if (across <= 1) throw new IllegalArgumentException("No more seams to remove");
        if (seam.length != along) throw new IllegalArgumentException("Seam must of length " + along);
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= across || (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1)) {
                throw new IllegalArgumentException("Invalid seam");
            }
        }
    }

}
