package io.github.avmohan.coursera.algs4.part1.week4;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void testThatConstructorMakesDefensiveCopyForImmutability() {
        int[][] blocks = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        Board board = new Board(blocks);
        blocks[0][0] = -1;
        assertThat(board.isGoal()).isTrue();
    }

    @Test
    void testThatDimensionReturnsSizeOfBoard() throws Exception {
        int[][] blocks = {
            {1, 2},
            {3, 0}
        };
        Board board = new Board(blocks);
        assertThat(board.dimension()).isEqualTo(2);
    }

    @Test
    void testThatHammingDistanceIsZeroForGoal() throws Exception {
        int[][] blocks = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        Board board = new Board(blocks);
        assertThat(board.hamming()).isZero();
    }


    @Test
    void testThatManhattanDistanceIsZeroForGoal() throws Exception {
        int[][] blocks = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        Board board = new Board(blocks);
        assertThat(board.manhattan()).isZero();
    }

    @Test
    void testHammingRandom() throws Exception {
        int[][] blocks = {
            {0, 4, 2},
            {3, 5, 6},
            {7, 8, 1}
        };
        Board board = new Board(blocks);
        assertThat(board.hamming()).isEqualTo(4);
    }

    @Test
    void testManhattanRandom() throws Exception {
        int[][] blocks = {
            {0, 4, 2},
            {3, 5, 6},
            {7, 8, 1}
        };
        Board board = new Board(blocks);
        assertThat(board.manhattan()).isEqualTo(10);
    }

    @Test
    void testIsGoalForGoalBoard() throws Exception {
        int[][] blocks = {
            {1, 2},
            {3, 0}
        };
        Board board = new Board(blocks);
        assertThat(board.isGoal()).isTrue();
    }

    @Test
    void testIsGoalForNonGoalBoard() throws Exception {
        int[][] blocks = {
            {0, 2},
            {3, 1}
        };
        Board board = new Board(blocks);
        assertThat(board.isGoal()).isFalse();
    }

    @Test
    void testEquals() throws Exception {
        int[][] blocks1 = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        int[][] blocks2 = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        int[][] blocks3 = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 6, 8}
        };
        assertThat(new Board(blocks1)).isNotEqualTo(null);
        assertThat(new Board(blocks1)).isNotEqualTo("Board");
        assertThat(new Board(blocks1)).isEqualTo(new Board(blocks1));
        assertThat(new Board(blocks1)).isEqualTo(new Board(blocks2));
        assertThat(new Board(blocks1)).isNotEqualTo(new Board(blocks3));
    }


    @Test
    void testNeighbours() throws Exception {
        int[][] blocks = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        int[][] neighbor1 = {
            {4, 1, 3},
            {0, 2, 5},
            {7, 8, 6}
        };
        int[][] neighbor2 = {
            {1, 0, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        Board board = new Board(blocks);
        assertThat(board.neighbors()).hasSize(2).containsExactlyInAnyOrder(new Board(neighbor1), new Board(neighbor2));
    }


}