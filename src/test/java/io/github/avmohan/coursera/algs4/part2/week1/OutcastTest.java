package io.github.avmohan.coursera.algs4.part2.week1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OutcastTest {

    private static String getPath(String s) {
        return WordNetTest.class.getResource(s).getPath();
    }

    @Test
    void testConstructorThrowsIaeIfWordnetIsNull() throws Exception {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Outcast(null));
    }

    @Test
    void testOutcast() throws Exception {
        WordNet wordNet = new WordNet(getPath("synsets15.txt"), getPath("hypernyms15.txt"));
        Outcast outcast = new Outcast(wordNet);
        assertThat(outcast.outcast(new String[]{"a", "e", "i", "k"}))
                .isEqualTo("i");
    }
}