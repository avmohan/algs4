package io.github.avmohan.coursera.algs4.part1.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// This is a 2d tree
public class KdTree {

    private static final Comparator<Point2D> X_COMP = Comparator.comparingDouble(Point2D::x);
    private static final Comparator<Point2D> Y_COMP = Comparator.comparingDouble(Point2D::y);

    private static Comparator<Point2D> comparator(int depth) {
        return (depth % 2 == 0) ? X_COMP : Y_COMP;
    }

    // Find the cut of curRect that is made by p
    private static RectHV getNewRect(RectHV curRect, Point2D p, int depth, int direction) {
        RectHV newRect;
        if (depth % 2 == 0) {
            if (direction == 0)
                // left
                newRect = new RectHV(curRect.xmin(), curRect.ymin(), p.x(), curRect.ymax());
            else
                // right
                newRect = new RectHV(p.x(), curRect.ymin(), curRect.xmax(), curRect.ymax());
        } else {
            if (direction == 0)
                // bottom
                newRect = new RectHV(curRect.xmin(), curRect.ymin(), curRect.xmax(), p.y());
            else
                // top
                newRect = new RectHV(curRect.xmin(), p.y(), curRect.xmax(), curRect.ymax());
        }
        return newRect;
    }

    // Each node corresponds to an axis-aligned rectangle in the unit square, which encloses all of the points in its subtree
    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    private Node root;
    private int size;

    // Construct an empty set of points
    public KdTree() {
        // Defaults
    }

    // Is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // Number of points in the set
    public int size() {
        return size;
    }

    // Add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null in insert()");
        RectHV rect = new RectHV(0, 0, 1, 1);
        root = insert(root, p, rect, 0);
    }

    // Insert point p into the (possibly null) subtree rooted at node, at given depth, and return the new subtree
    private Node insert(Node node, Point2D p, RectHV rect, int depth) {
        // Reached leaf without finding p, so create a new node and add p
        assert rect != null;
        if (node == null) {
            size++;
            return new Node(p, rect);
        }

        // Tree contains p so don't add again.
        if (p.equals(node.p)) {
            assert rect.equals(node.rect);
            return node;
        }

        // Add on left or right subtree based on comparison of appropriate dimension
        if (comparator(depth).compare(p, node.p) <= 0) {
            RectHV newRect = getNewRect(rect, node.p, depth, 0);
            node.lb = insert(node.lb, p, newRect, depth + 1);
        } else {
            RectHV newRect = getNewRect(rect, node.p, depth, 1);
            node.rt = insert(node.rt, p, newRect, depth + 1);
        }
        return node;
    }

    // Does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null in contains()");
        return contains(root, p, 0);
    }

    private boolean contains(Node node, Point2D p, int depth) {
        assert p != null;
        if (node == null)
            return false;
        if (p.equals(node.p))
            return true;
        if (comparator(depth).compare(p, node.p) <= 0)
            return contains(node.lb, p, depth + 1);
        return contains(node.rt, p, depth + 1);
    }

    // Draw all points to standard draw
    public void draw() {
        draw(root, 0);
    }

    private void draw(Node node, int depth) {
        // Traverse tree in preorder and draw all points
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());

        if (depth % 2 == 0) {
            // split along x dimension - draw a vertical red line
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            // split along y dimension - draw a horizontal blue line
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        draw(node.lb, depth + 1);
        draw(node.rt, depth + 1);
    }

    // TODO: Implement range & nearest.
    // All points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("rect cannot be null in range()");
        List<Point2D> points = new ArrayList<>();
        range(root, rect, points);
        return points;
    }

    private void range(Node node, RectHV rect, List<Point2D> points) {
        // traverse tree, visiting both sides, but go down a node only if rect.intersects(node.getNewRect)
        if (node == null) return;
        if (!rect.intersects(node.rect)) return;
        if (rect.contains(node.p)) points.add(node.p);
        range(node.lb, rect, points);
        range(node.rt, rect, points);
    }


    // A nearest neighbour in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p cannot be null in nearest");
        if (isEmpty()) return null;
        PointDistPair champ = new PointDistPair(root.p, p.distanceSquaredTo(root.p));
        return nearestQuery(root, p, champ, 0).point;
    }

    private static class PointDistPair {
        final Point2D point;
        final double distSquared;

        PointDistPair(Point2D point, double distSquared) {
            this.point = point;
            this.distSquared = distSquared;
        }
    }

    private PointDistPair nearestQuery(Node node, Point2D queryPoint, PointDistPair champ, int depth) {
        if (node != null && node.rect.distanceSquaredTo(queryPoint) < champ.distSquared) {
            PointDistPair nodeDistPair = new PointDistPair(node.p, node.p.distanceSquaredTo(queryPoint));
            if (nodeDistPair.distSquared < champ.distSquared) champ = nodeDistPair;
            if (comparator(depth).compare(queryPoint, node.p) <= 0) {
                // go lb then rt
                champ = nearestQuery(node.lb, queryPoint, champ, depth + 1);
                champ = nearestQuery(node.rt, queryPoint, champ, depth + 1);
            } else {
                // go rt then lb
                champ = nearestQuery(node.rt, queryPoint, champ, depth + 1);
                champ = nearestQuery(node.lb, queryPoint, champ, depth + 1);
            }
        }
        return champ;
    }
}
