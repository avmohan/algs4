package io.github.avmohan.coursera.algs4.part2.week1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class WordNetTest {

    private static final String VALID_SYNSET = getPath("synsets15.txt");
    private static final String VALID_HYPERNYM = getPath("hypernyms15.txt");
    private static final String CYCLIC_HYPERNYMS = getPath("hypernyms15Cycle.txt");
    private static final String UNROOTED_DAG_HYPERNYM = getPath("hypernyms15MultipleRoots.txt");

    private static String getPath(String s) {
        return WordNetTest.class.getResource(s).getPath();
    }

    @Test
    void testConstructorThrowsIaeIfSynsetsIsNull() throws Exception {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new WordNet(null, VALID_HYPERNYM))
                .withMessage("synsets & hypernyms should be non-null");
    }

    @Test
    void testConstructorThrowsIaeIfHypernymsIsNull() throws Exception {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new WordNet(VALID_SYNSET, null))
                .withMessage("synsets & hypernyms should be non-null");
    }

    @Test
    void testConstructorThrowsIaeIfSynsetsFileDoesNotExist() throws Exception {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new WordNet("asasddf", VALID_HYPERNYM))
                .withMessage("Could not open asasddf");
    }

    @Test
    void testConstructorThrowsIaeIfHypernymsFileDoesNotExist() throws Exception {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new WordNet(VALID_SYNSET, "qweas"))
                .withMessage("Could not open qweas");
    }

    @Test
    void testConstructorThrowsIaeIfDigraphHasCycle() throws Exception {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new WordNet(VALID_SYNSET, CYCLIC_HYPERNYMS))
                .withMessage("digraph is not a rooted dag");
    }

    @Test
    void testConstructorThrowsIaeIfDagIsNotRooted() throws Exception {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new WordNet(VALID_SYNSET, UNROOTED_DAG_HYPERNYM))
                .withMessage("digraph is not a rooted dag");
    }

    @Test
    void testIsNounThrowsIaeIfWordIsNull() throws Exception {
        WordNet wordNet = new WordNet(VALID_SYNSET, VALID_HYPERNYM);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> wordNet.isNoun(null))
                .withMessage("word must be non-null");
    }

    @Test
    void testDistanceThrowsIaeIfEitherNounIsNull() throws Exception {
        WordNet wordNet = new WordNet(VALID_SYNSET, VALID_HYPERNYM);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> wordNet.distance("k", null))
                .withMessage("word must be non-null");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> wordNet.distance(null, "k"))
                .withMessage("word must be non-null");
    }

    @Test
    void testSapThrowsIaeIfEitherNounIsNull() throws Exception {
        WordNet wordNet = new WordNet(VALID_SYNSET, VALID_HYPERNYM);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> wordNet.sap("k", null))
                .withMessage("word must be non-null");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> wordNet.sap(null, "k"))
                .withMessage("word must be non-null");
    }

    @Test
    void testDistanceThrowsIaeIfEitherNounIsNotInTheWordnet() throws Exception {
        WordNet wordNet = new WordNet(VALID_SYNSET, VALID_HYPERNYM);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> wordNet.distance("k", "r"))
                .withMessage("Both nouns must be present in wordnet");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> wordNet.distance("r", "k"))
                .withMessage("Both nouns must be present in wordnet");
    }

    @Test
    void testSapThrowsIaeIfEitherNounIsNotInTheWordNet() throws Exception {
        WordNet wordNet = new WordNet(VALID_SYNSET, VALID_HYPERNYM);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> wordNet.sap("k", "r"))
                .withMessage("Both nouns must be present in wordnet");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> wordNet.sap("r", "k"))
                .withMessage("Both nouns must be present in wordnet");
    }

    @Test
    void testDistance1() throws Exception {
        WordNet wordNet = new WordNet(VALID_SYNSET, VALID_HYPERNYM);
        assertThat(wordNet.distance("i", "o"))
                .isEqualTo(7);
    }

    @Test
    void testSap1() throws Exception {
        WordNet wordNet = new WordNet(VALID_SYNSET, VALID_HYPERNYM);
        assertThat(wordNet.sap("i", "o"))
                .isEqualTo("b");
    }
}