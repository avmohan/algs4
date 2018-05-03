package io.github.avmohan.coursera.algs4.part2.week1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class SAPTest {

    @Test
    public void testConstructorThrowsIaeIfGivenNullDigraph() throws Exception {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new SAP(null));
    }

    @Test
    public void testInvalidNodeThrowsIae() throws Exception {
        Digraph digraph = new Digraph(5);
        SAP sap = new SAP(digraph);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sap.ancestor(-1, 3));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sap.length(3, 5));
    }


    @Test
    public void testNullIterableThrowsIae() throws Exception {
        Digraph digraph = new Digraph(5);
        SAP sap = new SAP(digraph);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> sap.ancestor(Arrays.asList(1, 2), null));
    }


    @Test
    public void testCommonScenario() throws Exception {
        SAP sap = new SAP(readDigraph("digraph1.txt"));
        assertThat(sap.ancestor(7, 11)).isEqualTo(1);
        assertThat(sap.length(7, 11)).isEqualTo(5);
    }

    @Test
    public void testDisconnectedNodes() throws Exception {
        SAP sap = new SAP(readDigraph("digraph1.txt"));
        assertThat(sap.ancestor(4, 6)).isEqualTo(-1);
        assertThat(sap.length(4, 6)).isEqualTo(-1);
    }

    @Test
    public void testSelfAncestor() throws Exception {
        SAP sap = new SAP(readDigraph("digraph1.txt"));
        assertThat(sap.ancestor(4, 4)).isEqualTo(4);
        assertThat(sap.length(4, 4)).isEqualTo(0);
    }

    @Test
    public void testParentChild() throws Exception {
        SAP sap = new SAP(readDigraph("digraph1.txt"));
        assertThat(sap.ancestor(0, 4)).isEqualTo(0);
        assertThat(sap.length(0, 4)).isEqualTo(2);
    }

    private Digraph readDigraph(String name) throws Exception {
        return new Digraph(new In(new File(getClass().getResource(name).getFile())));
    }

}