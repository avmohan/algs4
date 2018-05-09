package io.github.avmohan.coursera.algs4.part2.week2;

import edu.princeton.cs.algs4.Picture;

import java.awt.*;

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


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // TODO
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        // TODO
        return null;
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
