package io.github.avmohan.coursera.algs4.week3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

/**
 * Created by avmohan on 08/11/17.
 */
class FastCollinearPointsTest {

    @Test
    void testConstructorThrowsIllegalArgumentIfPointsArrayIsNull() throws Exception {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new FastCollinearPoints(null))
            .withMessage("Points cannot be null");
    }

    @Test
    void testConstructorThrowsIllegalArgumentIfAnyPointIsNull() throws Exception {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new FastCollinearPoints(new Point[] {null}))
            .withMessage("Points cannot contain null points");
    }

    @Test
    void testConstructorThrowsIllegalArgumentIfAnyPointIsDuplicated() throws Exception {
        Point[] points = {new Point(3, 4), new Point(1, 2), new Point(3, 4)};
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new FastCollinearPoints(points))
            .withMessage("Points cannot contain duplicates");
    }

    @Test
    void testInput8() throws Exception {
        Point[] points = {
            new Point(10000, 0),
            new Point(0, 10000),
            new Point(3000, 7000),
            new Point(7000, 3000),
            new Point(20000, 21000),
            new Point(3000, 4000),
            new Point(14000, 15000),
            new Point(6000, 7000)
        };
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        assertThat(fcp.numberOfSegments()).isEqualTo(2);
    }

}