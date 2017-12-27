package io.github.avmohan.coursera.algs4.part1.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by avmohan on 26/10/17.
 */
public class BruteCollinearPoints {
    private final Point[] points;
    private List<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        validatePoints(points);
        this.points = Arrays.copyOf(points, points.length);
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        computeSegments();
        return segments.toArray(new LineSegment[0]);
    }

    private void validatePoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points cannot be null");
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Points cannot contain null points");
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Points cannot contain duplicates");
                }
            }
        }
    }

    private void computeSegments() {
        if (segments != null) {
            return;
        }
        segments = new ArrayList<>();
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    double slope_ij = points[i].slopeTo(points[j]);
                    double slope_ik = points[i].slopeTo(points[k]);
                    if (slope_ij == slope_ik) {
                        for (int l = k + 1; l < points.length; l++) {
                            double slope_il = points[i].slopeTo(points[l]);
                            if (slope_ij == slope_il) {
                                segments.add(new LineSegment(points[i], points[l]));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
