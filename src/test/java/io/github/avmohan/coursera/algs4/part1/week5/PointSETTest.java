package io.github.avmohan.coursera.algs4.part1.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PointSETTest {
    @Test
    void testIsEmpty() throws Exception {
        PointSET set = new PointSET();
        assertThat(set.isEmpty()).isTrue();
    }

    @Test
    void testIsEmptyFalse() throws Exception {
        PointSET set = new PointSET();
        set.insert(new Point2D(0, 0));
        assertThat(set.isEmpty()).isFalse();
    }

    @Test
    void testSizeOnEmptySet() throws Exception {
        PointSET set = new PointSET();
        assertThat(set.size()).isZero();
    }

    @Test
    void testSizeOnNonEmptySet() throws Exception {
        PointSET set = new PointSET();
        set.insert(new Point2D(0, 0));
        set.insert(new Point2D(0, 1));
        assertThat(set.size()).isEqualTo(2);
    }

    @Test
    void testDuplicateElementsAreOnlyInsertedOnce() throws Exception {
        PointSET set = new PointSET();
        set.insert(new Point2D(0, 0));
        set.insert(new Point2D(0, 0));
        assertThat(set.size()).isEqualTo(1);
    }

    @Test
    void testInsertAndContains() throws Exception {
        PointSET set = new PointSET();
        set.insert(new Point2D(0, 0));
        set.insert(new Point2D(1, 0));
        assertThat(set.contains(new Point2D(0, 0))).isTrue();
        assertThat(set.contains(new Point2D(-1, 0))).isFalse();
    }

    @Test
    void testInsertingNullPoint() throws Exception {
        PointSET set = new PointSET();
        assertThatIllegalArgumentException()
            .isThrownBy(() -> set.insert(null));
    }

    @Test
    void testRangeForNullRect() throws Exception {
        PointSET set = new PointSET();
        assertThatIllegalArgumentException()
            .isThrownBy(() -> set.range(null));
    }

    @Test
    void testNearestForNullPoint() throws Exception {
        PointSET set = new PointSET();
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
            .containsExactlyInAnyOrder(
                new Point2D(0.372, 0.497),
                new Point2D(0.144, 0.179),
                new Point2D(0.417, 0.362),
                new Point2D(0.499, 0.208)
            );
    }

    @Test
    void testNearest() throws Exception {
        assertThat(input10().nearest(new Point2D(0.5, 0.5)))
            .isEqualTo(new Point2D(0.564, 0.413));
    }


    @Test
    void testNearestReturnsNullForEmptySet() throws Exception {
        assertThat(new PointSET().nearest(new Point2D(0, 0))).isNull();
    }


    private PointSET input10() {
        PointSET set = new PointSET();
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