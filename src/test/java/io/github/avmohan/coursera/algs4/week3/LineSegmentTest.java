package io.github.avmohan.coursera.algs4.week3;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;

/**
 * Created by avmohan on 26/10/17.
 */
class LineSegmentTest {
    @Test
    void testConstructorThrowsNpeWhenEitherPointIsNull() throws Exception {
        Point p = new Point(3, 4);
        assertThatNullPointerException().isThrownBy(() -> new LineSegment(null, p));
        assertThatNullPointerException().isThrownBy(() -> new LineSegment(p, null));
        assertThatNullPointerException().isThrownBy(() -> new LineSegment(null, null));
    }

    @Test
    void testThatHashCodeThrowsUnsupportedOperation() throws Exception {
        LineSegment l = new LineSegment(new Point(1, 3), new Point(5, 6));
        assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(l::hashCode);
    }


}