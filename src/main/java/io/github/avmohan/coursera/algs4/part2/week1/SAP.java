package io.github.avmohan.coursera.algs4.part2.week1;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Collections;

public class SAP {

    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {
        if (digraph == null) throw new IllegalArgumentException("digraph cannot be null");
        this.digraph = new Digraph(digraph);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return sapHelper(v, w).length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return sapHelper(v, w).ancestor;
    }

    // length of shortest ancestral path between any vertex in vs and any vertex in ws; -1 if no such path
    public int length(Iterable<Integer> vs, Iterable<Integer> ws) {
        return sapHelper(vs, ws).length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> vs, Iterable<Integer> ws) {
        return sapHelper(vs, ws).ancestor;
    }

    private void validate(int v) {
        if (v < 0 || v >= digraph.V()) throw new IllegalArgumentException("Node " + v + " is invalid");
    }

    private void validate(Iterable<Integer> vs) {
        vs.forEach(this::validate);
    }

    private SapAncestor sapHelper(int v, int w) {
        return sapHelper(Collections.singletonList(v), Collections.singletonList(w));
    }

    private SapAncestor sapHelper(Iterable<Integer> vs, Iterable<Integer> ws) {
        validate(vs);
        validate(ws);
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(digraph, vs);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(digraph, ws);
        int min = Integer.MAX_VALUE;
        SapAncestor sapAncestor = new SapAncestor(-1, -1);
        for (int i = 0; i < digraph.V(); i++) {
            int dist = vPaths.hasPathTo(i) && wPaths.hasPathTo(i) ?
                    vPaths.distTo(i) + wPaths.distTo(i) :
                    Integer.MAX_VALUE;
            if (dist < min) {
                min = dist;
                sapAncestor = new SapAncestor(i, dist);
            }
        }
        return sapAncestor;
    }

    private static class SapAncestor {
        private final int ancestor;
        private final int length;

        private SapAncestor(int ancestor, int length) {
            this.ancestor = ancestor;
            this.length = length;
        }
    }

}
