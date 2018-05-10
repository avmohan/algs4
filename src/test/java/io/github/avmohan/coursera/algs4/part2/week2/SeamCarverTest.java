package io.github.avmohan.coursera.algs4.part2.week2;

import edu.princeton.cs.algs4.Picture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class SeamCarverTest {

    private SeamCarver sc;
    private int[] seam;

    @Test
    void constructorThrowsIaeIfNullPicture() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new SeamCarver(null));
    }

    @Test
    void removeVerticalSeamThrowsIaeIfNullSeam() {
        Picture pic = new Picture(5, 5);
        SeamCarver sc = new SeamCarver(pic);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeVerticalSeam(null));
    }

    @Test
    void removeHorizontalSeamThrowsIaeIfNullSeam() {
        Picture pic = new Picture(5, 5);
        SeamCarver sc = new SeamCarver(pic);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeHorizontalSeam(null));
    }

    @Test
    void removeVerticalSeamThrowsIaeIfWidthIsOneOrLess() {
        Picture pic = new Picture(1, 5);
        SeamCarver sc = new SeamCarver(pic);
        int seam[] = {0, 1, -1, 2, 3};
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeVerticalSeam(seam));
    }

    @Test
    void removeHorizontalSeamThrowsIaeIfHeightIsOneOrLess() {
        Picture pic = new Picture(5, 1);
        SeamCarver sc = new SeamCarver(pic);
        int seam[] = {0, 1, -1, 2, 3};
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeHorizontalSeam(seam));
    }

    @Test
    void removeVerticalSeamThrowsIaeIfSeamLengthIsNotEqualToHeight() {
        Picture pic = new Picture(2, 5);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = {0, 1};
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeVerticalSeam(seam));
    }

    @Test
    void removeHorizontalSeamThrowsIaeIfSeamLengthIsNotEqualToWidth() {
        Picture pic = new Picture(2, 5);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = {0, 1, 0, 1, 0};
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeHorizontalSeam(seam));
    }

    @Test
    void removeVerticalSeamThrowsIaeIfSeamContainsOutOfRangeIndices() {
        Picture pic = new Picture(3, 5);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = {0, 1, 2, 3, 2};
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeVerticalSeam(seam));
    }

    @Test
    void removeHorizontalSeamThrowsIaeIfSeamContainsOutOfRangeIndices() {
        Picture pic = new Picture(3, 5);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = {0, 5, 2};
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeHorizontalSeam(seam));
    }

    @Test
    void removeVerticalSeamThrowsIaeIfSeamIsNotContinuous() {
        Picture pic = new Picture(5, 5);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = {0, 1, 3, 1, 0};
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeVerticalSeam(seam));
    }

    @Test
    void removeHorizontalSeamThrowsIaeIfSeamIsNotContinuous() {
        Picture pic = new Picture(5, 5);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = {0, 1, 0, 1, 3};
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.removeHorizontalSeam(seam));
    }

    private static Stream<Arguments> outOfBoundCellsForThreeByFiveGrid() {
        int[][] outOfBoundCells = {{-1, -1}, {-1, 1}, {1, -1}, {3, 1}, {1, 5}, {3, 5}};
        return Stream.of(outOfBoundCells).map(cell -> Arguments.of(cell[0], cell[1]));
    }

    @ParameterizedTest(name = "energy({0}, {1}) should throw IllegalArgumentException for 5x5 pic")
    @MethodSource("outOfBoundCellsForThreeByFiveGrid")
    void energyThrowsIaeIfCoordinatesAreInvalid(int x, int y) {
        Picture pic = new Picture(3, 5);
        SeamCarver sc = new SeamCarver(pic);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sc.energy(x, y));
    }

    @Test
    void removeVerticalSeamReducesWidthByOne() {
        Picture pic = new Picture(5, 5);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = {0, 1, 0, 1, 2};
        sc.removeVerticalSeam(seam);
        assertThat(sc.picture().width()).isEqualTo(4);
    }

    @Test
    void removeHorizontalSeamReducesHeightByOne() {
        Picture pic = new Picture(5, 5);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = {0, 1, 0, 1, 2};
        sc.removeHorizontalSeam(seam);
        assertThat(sc.picture().height()).isEqualTo(4);
    }

    @Test
    void pictureGivenToConstructorIsNotMutated() {
        Picture pic = new Picture(5, 5);
        Picture pic2 = new Picture(pic);
        SeamCarver sc = new SeamCarver(pic);
        int[] seam = {0, 1, 0, 1, 2};
        sc.removeHorizontalSeam(seam);
        assertThat(pic).isEqualTo(pic2);
    }

    @Test
    void findHorizontalSeamGivesSeamOfCorrectLength() {
        Picture pic = new Picture(4, 5);
        SeamCarver sc = new SeamCarver(pic);
        assertThat(sc.findHorizontalSeam()).hasSize(4);
    }

    @Test
    void findVerticalSeamGivesSeamOfCorrectLength() {
        Picture pic = new Picture(4, 5);
        SeamCarver sc = new SeamCarver(pic);
        assertThat(sc.findVerticalSeam()).hasSize(5);
    }
}