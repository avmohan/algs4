package io.github.avmohan.coursera.algs4.week1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Created by avmohan on 19/10/17.
 */
public class PercolationTest {

    @Test
    public void testThatConstructorThrowsIllegalArgumentExceptionWhenSizeNotPositive() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() -> new Percolation(-5));
        assertThatIllegalArgumentException().isThrownBy(() -> new Percolation(0));
    }

    private static Stream<Arguments> outOfBoundCellsForSizeFiveGrid() {
        int[][] outOfBoundCells = {{0, 0}, {0, 2}, {2, 0}, {6, 2}, {2, 6}, {6, 6}};
        return Stream.of(outOfBoundCells).map(cell -> Arguments.of(cell[0], cell[1]));
    }

    @ParameterizedTest(name = "Percolation(5).open({0}, {1}) should throw IllegalArgumentException")
    @MethodSource("outOfBoundCellsForSizeFiveGrid")
    public void testBoundsCheckingForOpen(int row, int col) throws Exception {
        Percolation p = new Percolation(5);
        assertThatIllegalArgumentException()
            .isThrownBy(() -> p.open(row, col))
            .withMessage("Row or column not within bounds");
    }

    @ParameterizedTest(name = "Percolation(5).isOpen({0}, {1}) should throw IllegalArgumentException")
    @MethodSource("outOfBoundCellsForSizeFiveGrid")
    public void testBoundsCheckingForIsOpen(int row, int col) throws Exception {
        Percolation p = new Percolation(5);
        assertThatIllegalArgumentException()
            .isThrownBy(() -> p.isOpen(row, col))
            .withMessage("Row or column not within bounds");
    }

    @ParameterizedTest(name = "Percolation(5).isFull({0}, {1}) should throw IllegalArgumentException")
    @MethodSource("outOfBoundCellsForSizeFiveGrid")
    public void testBoundsCheckingForIsFull(int row, int col) throws Exception {
        Percolation p = new Percolation(5);
        assertThatIllegalArgumentException()
            .isThrownBy(() -> p.isFull(row, col))
            .withMessage("Row or column not within bounds");
    }


    @Test
    public void testThatSingleCellGridCanPercolate() throws Exception {
        Percolation p = new Percolation(1);
        assertThat(p.percolates()).isFalse();
        p.open(1, 1);
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void testThatDiagonalPercolationDoesNotHappen() throws Exception {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 2);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void testThatBackWashDoesNotHappen() throws Exception {
        Percolation p = new Percolation(3);
        p.open(2, 1);
        p.open(3, 1);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        assertThat(p.percolates()).isTrue();
        assertThat(p.isFull(3, 3)).isTrue();
        assertThat(p.isFull(2, 1)).isFalse();
    }

    @Test
    public void testNumberOfOpenSites() throws Exception {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(3, 3);
        p.open(3, 1);
        assertThat(p.numberOfOpenSites()).isEqualTo(4);
    }


}