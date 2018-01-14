package io.github.avmohan.coursera.algs4.part1.week4;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class SolverTest {

    @Test
    void testThatNullBoardThrowsIAE() throws Exception {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Solver(null));
    }

}