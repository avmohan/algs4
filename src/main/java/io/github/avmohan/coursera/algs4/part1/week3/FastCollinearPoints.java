package io.github.avmohan.coursera.algs4.part1.week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by avmohan on 07/11/17.
 */
public class FastCollinearPoints {

    private final Point[] points;
    private List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
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

    private void computeSegments() {
        if (segments != null) {
            return;
        }
        segments = new ArrayList<>();
        Point[] copy = Arrays.copyOf(points, points.length);
        for (int i = 0; i < copy.length; i++) {
            Point curPoint = copy[i];
            Arrays.sort(points, curPoint.slopeOrder());
            // points[0] & curPoint should refer to same point since slopeTo(p, p) should be NEG_INF so should be first
            // element when sorted by curPoint.slopeOrder()
            assert curPoint == points[0];

            // Find maximal runs of 3 or more points with equal slopeTo(curPoint, _) in points[1:]
            int j = 1;
            while (j < points.length) {
                int k = j + 1;
                while (k < points.length && curPoint.slopeTo(points[j]) == curPoint.slopeTo(points[k])) {
                    k++;
                }
                // points[j:k] is a maximal run of points with same slope.
                // If we have a run of more than 3 points i.e. 4 if we include curPoint also.
                if (k - j >= 3) {
                    List<Point> segmentPoints = Arrays.asList(points).subList(j, k);
                    Point minPoint = Collections.min(segmentPoints);
                    // To avoid partial line segments and double counting by reversing the order of points,
                    // we associate each segment with the minimum (by natural ordering) point in the set of points that
                    // make up the segment. Since the segmentPoints list does not include curPoint, curPoint should be
                    // less than the min Point of segmentPoints in order to count this segment.
                    if (curPoint.compareTo(minPoint) < 0) {
                        Point maxPoint = Collections.max(segmentPoints);
                        segments.add(new LineSegment(curPoint, maxPoint));
                    }
                }
                j = k;
            }
        }
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
}
