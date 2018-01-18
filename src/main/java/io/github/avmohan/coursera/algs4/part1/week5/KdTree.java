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

    private Node root;
    private int size;

    // Each node corresponds to an axis-aligned rectangle in the unit square, which encloses all of the points in its subtree
    private static class Node {
        private final Point2D p;      // the point
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.p = p;
        }

        @Override
        public String toString() {
            return "Node{" +
                "p=" + p +
                ", lb=" + lb +
                ", rt=" + rt +
                '}';
        }
    }

    private enum Dimension {
        X, Y;
    }

    private static Dimension cutDimension(int depth) {
        if (depth % 2 == 0) return Dimension.X;
        return Dimension.Y;
    }

    private static Comparator<Point2D> comparator(int depth) {
        return (cutDimension(depth) == Dimension.X) ? X_COMP : Y_COMP;
    }

    private static RectHV left(RectHV rect, Point2D p) {
        return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
    }

    private static RectHV right(RectHV rect, Point2D p) {
        return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
    }

    private static RectHV bottom(RectHV rect, Point2D p) {
        return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
    }

    private static RectHV top(RectHV rect, Point2D p) {
        return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
    }

    private RectHV[] nextRects(RectHV rect, Point2D p, int depth) {
        if (cutDimension(depth) == Dimension.X) return new RectHV[] {left(rect, p), right(rect, p)};
        else return new RectHV[] {bottom(rect, p), top(rect, p)};
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
        root = insert(root, 0, p);
    }

    // Insert point p into the (possibly null) subtree rooted at node, at given depth, and return the new subtree
    private Node insert(Node node, int depth, Point2D p) {
        // Reached leaf without finding p, so create a new node and add p
        if (node == null) {
            size++;
            return new Node(p);
        }

        // Tree contains p so don't add again.
        if (p.equals(node.p)) {
            return node;
        }

        // Add on left or right subtree based on comparison of appropriate dimension
        if (comparator(depth).compare(p, node.p) < 0) {
            node.lb = insert(node.lb, depth + 1, p);
        } else {
            node.rt = insert(node.rt, depth + 1, p);
        }
        return node;
    }

    // Does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null in contains()");
        return contains(root, 0, p);
    }

    private boolean contains(Node node, int depth, Point2D p) {
        assert p != null;
        if (node == null)
            return false;
        if (p.equals(node.p))
            return true;
        if (comparator(depth).compare(p, node.p) < 0)
            return contains(node.lb, depth + 1, p);
        return contains(node.rt, depth + 1, p);
    }

    // Draw all points to standard draw
    public void draw() {
        draw(root, 0, new RectHV(0, 0, 1, 1));
    }

    private void draw(Node node, int depth, RectHV rect) {
        // Traverse tree in preorder and draw all points
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());

        if (cutDimension(depth) == Dimension.X) {
            // split along x dimension - draw a vertical red line
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), rect.ymin(), node.p.x(), rect.ymax());
        } else {
            // split along y dimension - draw a horizontal blue line
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), node.p.y(), rect.xmax(), node.p.y());
        }
        RectHV[] nextRects = nextRects(rect, node.p, depth);
        draw(node.lb, depth + 1, nextRects[0]);
        draw(node.rt, depth + 1, nextRects[1]);
    }

    // All points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV queryRect) {
        if (queryRect == null) throw new IllegalArgumentException("rect cannot be null in range()");
        List<Point2D> points = new ArrayList<>();
        range(root, 0, new RectHV(0, 0, 1, 1), points, queryRect);
        return points;
    }

    private void range(Node node, int depth, RectHV rect, List<Point2D> points, RectHV queryRect) {
        // traverse tree, visiting both sides, but go down a node only if rect.intersects(node.getNewRect)
        if (node == null) return;
        if (!queryRect.intersects(rect)) return;
        if (queryRect.contains(node.p)) points.add(node.p);
        RectHV[] nextRects = nextRects(rect, node.p, depth);
        range(node.lb, depth + 1, nextRects[0], points, queryRect);
        range(node.rt, depth + 1, nextRects[1], points, queryRect);
    }


    // A nearest neighbour in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p cannot be null in nearest");
        if (isEmpty()) return null;
        PointDistPair champ = new PointDistPair(root.p, p.distanceSquaredTo(root.p));
        return nearestQuery(root, 0, new RectHV(0, 0, 1, 1), p, champ).point;
    }

    private static class PointDistPair {
        final Point2D point;
        final double distSquared;

        PointDistPair(Point2D point, double distSquared) {
            this.point = point;
            this.distSquared = distSquared;
        }

        @Override
        public String toString() {
            return "PointDistPair{" +
                "point=" + point +
                ", distSquared=" + distSquared +
                '}';
        }
    }

    private PointDistPair nearestQuery(Node node, int depth, RectHV rect, Point2D queryPoint, PointDistPair champ) {
        if (node != null && rect.distanceSquaredTo(queryPoint) < champ.distSquared) {
            PointDistPair nodeDistPair = new PointDistPair(node.p, node.p.distanceSquaredTo(queryPoint));
            if (nodeDistPair.distSquared < champ.distSquared) champ = nodeDistPair;
            RectHV[] nextRects = nextRects(rect, node.p, depth);
            if (comparator(depth).compare(queryPoint, node.p) < 0) {
                // go lb then rt
                champ = nearestQuery(node.lb, depth + 1, nextRects[0], queryPoint, champ);
                champ = nearestQuery(node.rt, depth + 1, nextRects[1], queryPoint, champ);
            } else {
                // go rt then lb
                champ = nearestQuery(node.rt, depth + 1, nextRects[1], queryPoint, champ);
                champ = nearestQuery(node.lb, depth + 1, nextRects[0], queryPoint, champ);
            }
        }
        return champ;
    }
}
