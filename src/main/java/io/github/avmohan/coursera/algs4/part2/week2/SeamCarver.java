package io.github.avmohan.coursera.algs4.part2.week2;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private final Picture picture;

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
        return 0.0;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, width(), height());
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam, height(), width());
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
