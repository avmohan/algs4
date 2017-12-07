package io.github.avmohan.coursera.algs4.week3;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Created by avmohan on 26/10/17.
 */
class PointTest {

    @Test
    void testNaturalOrderWhenBothCoordinatesDiffer() throws Exception {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        assertThat(p1).isLessThan(p2);
        assertThat(p2).isGreaterThan(p1);
    }

    @Test
    void testNaturalOrderWhenYCoordinatesDifferButXCoordinatesAreSame() throws Exception {
        Point p1 = new Point(4, 5);
        Point p2 = new Point(4, 6);
        assertThat(p1).isLessThan(p2);
        assertThat(p2).isGreaterThan(p1);
    }

    @Test
    void testNaturalOrderWhenYCoordinatesAreSameButXCoordinatesDiffer() throws Exception {
        Point p1 = new Point(1, 10);
        Point p2 = new Point(2, 10);
        assertThat(p1).isLessThan(p2);
        assertThat(p2).isGreaterThan(p1);
    }

    @Test
    void testNaturalOrderWhenBothCoordinatesAreSame() throws Exception {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        assertThat(p1).isEqualByComparingTo(p2);
        assertThat(p2).isEqualByComparingTo(p1);
    }


    @Test
    void testPositiveSlope() throws Exception {
        Point p1 = new Point(2, 2);
        Point p2 = new Point(3, 3);
        assertThat(p1.slopeTo(p2)).isPositive();
    }

    @Test
    void testNegativeSlope() throws Exception {
        Point p1 = new Point(2, 2);
        Point p2 = new Point(3, 1);
        assertThat(p1.slopeTo(p2)).isNegative();
    }

    @Test
    void testSlopeToIsSymmetric() throws Exception {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 4);
        assertThat(p1.slopeTo(p2)).isEqualTo(p2.slopeTo(p1));
    }

    @Test
    void testSlopeToForHorizontalLineSegment() throws Exception {
        Point p1 = new Point(3, 5);
        Point p2 = new Point(7, 5);
        assertThat(p1.slopeTo(p2)).isEqualTo(0.0);
        assertThat(p2.slopeTo(p1)).isEqualTo(0.0);
    }

    @Test
    void testSlopeForVerticalLineSegment() throws Exception {
        Point p1 = new Point(3, 5);
        Point p2 = new Point(3, 7);
        assertThat(p1.slopeTo(p2)).isEqualTo(Double.POSITIVE_INFINITY);
    }

    @Test
    void testSlopeForDegenerateLineSegment() throws Exception {
        Point p1 = new Point(3, 5);
        Point p2 = new Point(3, 5);
        assertThat(p1.slopeTo(p2)).isEqualTo(Double.NEGATIVE_INFINITY);
    }

    @Test
    void testPositiveSlopeValueGreaterThanOne() throws Exception {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(5, 8);
        assertThat(p1.slopeTo(p2)).isEqualTo(2);
    }

    @Test
    void testPositiveSlopeValueLessThanOne() throws Exception {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(5, 5);
        assertThat(p1.slopeTo(p2)).isEqualTo(0.5);
    }

    @Test
    void testPositiveSlopeValueEqualToOne() throws Exception {
        Point p1 = new Point(6, 8);
        Point p2 = new Point(3, 5);
        assertThat(p1.slopeTo(p2)).isEqualTo(1);
    }

    @Test
    void testNegativeSlopeValueGreaterThanOne() throws Exception {
        Point p1 = new Point(3, 9);
        Point p2 = new Point(7, 1);
        assertThat(p1.slopeTo(p2)).isEqualTo(-2);
    }

    @Test
    void testNegativeSlopeValueLessThanOne() throws Exception {
        Point p1 = new Point(3, 9);
        Point p2 = new Point(7, 7);
        assertThat(p1.slopeTo(p2)).isEqualTo(-0.5);
    }

    @Test
    void testNegativeSlopeValueEqualToOne() throws Exception {
        Point p1 = new Point(3, 9);
        Point p2 = new Point(7, 5);
        assertThat(p1.slopeTo(p2)).isEqualTo(-1);
    }

    @Test
    void testSlopeOrderComparator() throws Exception {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(2, 3);
        Point p3 = new Point(0, 5);
        assertThat(p1).usingComparator(p3.slopeOrder()).isLessThan(p2);
        assertThat(p1).usingComparator(p2.slopeOrder()).isGreaterThan(p3);
        assertThat(p3).usingComparator(p1.slopeOrder()).isLessThan(p2);
    }

    @Test
    void test_AddFailingTestForInaccurateSlope() throws Exception {
        Point p1 = new Point(466, 430);
        Point p2 = new Point(2, 153);
        assertThat(p1.slopeTo(p2)).isEqualTo(0.5969827586206896);
    }

}