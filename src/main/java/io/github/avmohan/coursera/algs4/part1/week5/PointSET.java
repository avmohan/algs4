package io.github.avmohan.coursera.algs4.part1.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> set;

    // Construct an empty set of points
    public PointSET() {
        set = new TreeSet<>();
    }

    // Is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // Number of points in the set
    public int size() {
        return set.size();
    }

    // Add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p cannot be null");
        set.add(p);
    }

    // Does the set contain point p
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // Draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        set.forEach(p -> StdDraw.point(p.x(), p.y()));
    }

    // All points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("rect cannot be null");
        return set.stream().filter(rect::contains)::iterator;
    }

    // A nearest neighbour in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point cannot be null");
        return set.stream().min(p.distanceToOrder()).orElse(null);
    }

}
