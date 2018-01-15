package io.github.avmohan.coursera.algs4.part1.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class KdTreeTest {

    @Test
    void testIsEmpty() throws Exception {
        KdTree set = new KdTree();
        assertThat(set.isEmpty()).isTrue();
    }

    @Test
    void testIsEmptyFalse() throws Exception {
        KdTree set = input10();
        assertThat(set.isEmpty()).isFalse();
    }

    @Test
    void testSizeOnEmptySet() throws Exception {
        KdTree set = new KdTree();
        assertThat(set.size()).isZero();
    }

    @Test
    void testSizeOnNonEmptySet() throws Exception {
        KdTree set = input10();
        assertThat(set.size()).isEqualTo(10);
    }

    @Test
    void testDuplicateElementsAreOnlyInsertedOnce() throws Exception {
        KdTree set = new KdTree();
        set.insert(new Point2D(0.372, 0.497));
        set.insert(new Point2D(0.564, 0.413));
        set.insert(new Point2D(0.226, 0.577));
        set.insert(new Point2D(0.372, 0.497));
        assertThat(set.size()).isEqualTo(3);
    }

    @Test
    void testInsertAndContains() throws Exception {
        KdTree set = new KdTree();
        set.insert(new Point2D(0.372, 0.497));
        set.insert(new Point2D(0.564, 0.413));
        set.insert(new Point2D(0.226, 0.577));
        assertThat(set.contains(new Point2D(0.564, 0.413))).isTrue();
        assertThat(set.contains(new Point2D(0.113, 0.22))).isFalse();
    }

    @Test
    void testInsertMultiplePointsWithSameCoordinateInOneDimension() throws Exception {
        KdTree set = new KdTree();
        Point2D p1 = new Point2D(0.372, 0.497);
        Point2D p2 = new Point2D(0.372, 0.332);
        Point2D p3 = new Point2D(0.623, 0.332);
        Point2D p4 = new Point2D(0.812, 0.332);
        Point2D p5 = new Point2D(0.372, 0.536);
        set.insert(p1);
        set.insert(p2);
        set.insert(p3);
        set.insert(p4);
        set.insert(p5);
        assertThat(set.contains(p1)).isTrue();
        assertThat(set.contains(p2)).isTrue();
        assertThat(set.contains(p3)).isTrue();
        assertThat(set.contains(p3)).isTrue();
        assertThat(set.contains(p4)).isTrue();
        assertThat(set.contains(p5)).isTrue();
    }


    @Test
    void testInsertingNullPoint() throws Exception {
        KdTree set = new KdTree();
        assertThatIllegalArgumentException()
            .isThrownBy(() -> set.insert(null));
    }

    @Test
    void testRangeForNullRect() throws Exception {
        KdTree set = new KdTree();
        assertThatIllegalArgumentException()
            .isThrownBy(() -> set.range(null));
    }

    @Test
    void testNearestForNullPoint() throws Exception {
        KdTree set = new KdTree();
        assertThatIllegalArgumentException()
            .isThrownBy(() -> set.nearest(null));
    }

    @Test
    void testRangeWhenNoPointsInRect() throws Exception {
        assertThat(input10().range(new RectHV(1, 1, 2, 2)))
            .isEmpty();
    }

    @Test
    void testRangeWhenSomePointsInRect() throws Exception {
        assertThat(input10().range(new RectHV(0, 0, 0.5, 0.5)))
            .hasSize(4);
    }

    @Test
    void testNearest() throws Exception {
        assertThat(input10().nearest(new Point2D(0.5, 0.5)))
            .isEqualTo(new Point2D(0.564, 0.413));
    }

    private KdTree input10() {
        KdTree set = new KdTree();
        double input[][] = {
            {0.372, 0.497},
            {0.564, 0.413},
            {0.226, 0.577},
            {0.144, 0.179},
            {0.083, 0.510},
            {0.320, 0.708},
            {0.417, 0.362},
            {0.862, 0.825},
            {0.785, 0.725},
            {0.499, 0.208}
        };
        Arrays.stream(input)
            .map(p -> new Point2D(p[0], p[1]))
            .forEach(set::insert);
        return set;
    }
}