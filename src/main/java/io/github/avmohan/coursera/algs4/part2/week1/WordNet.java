package io.github.avmohan.coursera.algs4.part2.week1;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.TopologicalX;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WordNet {
    private static final String COMMA = ",";
    private static final String SPACE = " ";

    /*
    Input format:
    Synsets - List of all synsets in WordNet.
    (synset_id, synset, gloss)
    synset_id -> int
    synset -> space separated list of nouns
    gloss -> Dictionary definitioon of the synset

    Hypernyms - List of hypernym relationships.
    (synset_id, <comma separated list of synset ids>)

    i.e:
    Synsets file has metadata associated with each node and
    Hypernyms file has the adjacency list representation of
    the wordnet.
     */

    // Perf reqs
    // Space        -> O(n)
    // Constructor  -> O(nlgn)
    // isNoun       -> O(lgn)
    // distance     -> O(n)
    // sap          -> O(n)

    // noun to synset_id mapping.
    private final Map<String, List<Integer>> nounMap;

    // List of synsets (required for sap method)
    private final List<String> synsets;

    private final SAP sap;

    // Constructor takes the name of the 2 input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("synsets & hypernyms should be non-null");
        }
        this.nounMap = new TreeMap<>();
        this.synsets = new ArrayList<>();
        In in = new In(synsets);
        String line;
        int numSynsets = 0;
        while ((line = in.readLine()) != null) {
            String[] fields = line.split(COMMA);
            if (fields.length != 3) throw new IllegalStateException();
            this.synsets.add(fields[1]);
            String[] nouns = fields[1].split(SPACE);
            for (String noun : nouns) {
                nounMap.computeIfAbsent(noun, x -> new ArrayList<>())
                        .add(Integer.parseInt(fields[0]));
            }
            numSynsets++;
        }

        // Digraph corresponding to the hypernym network.
        Digraph digraph = new Digraph(numSynsets);
        in = new In(hypernyms);
        while ((line = in.readLine()) != null) {
            String[] fields = line.split(COMMA, 2);
            if (fields.length != 2) throw new IllegalStateException();
            int src = Integer.parseInt(fields[0]);
            String[] neighbours = fields[1].split(COMMA);
            for (String neighbour : neighbours) {
                int dst = Integer.parseInt(neighbour);
                digraph.addEdge(src, dst);
            }
        }
        if (!isRootedDag(digraph)) {
            throw new IllegalArgumentException("digraph is not a rooted dag");
        }

        sap = new SAP(digraph);
    }

    private static boolean isRootedDag(Digraph digraph) {
        // To check if this is a rooted dag, we do a topological sort on the digraph
        // with all edges reversed.
        Digraph reverse = digraph.reverse();
        TopologicalX topology = new TopologicalX(digraph.reverse());
        // if no topological order, it means there is a cycle.
        if (!topology.hasOrder()) {
            return false;
        }
        // If digraph is rooted, root will come first in topological order of reverse digraph
        // and all nodes will be reachable from root in the reverse digraph.
        int root = topology.order().iterator().next();
        BreadthFirstDirectedPaths paths = new BreadthFirstDirectedPaths(reverse, root);
        for (int i = 0; i < reverse.V(); i++) {
            if (!paths.hasPathTo(i)) {
                return false;
            }
        }
        return true;
    }

    // returns all WordNet nouns [O(1)]
    public Iterable<String> nouns() {
        return nounMap.keySet();
    }

    // Is the word a noun [O(lgn)]
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word must be non-null");
        }
        return nounMap.containsKey(word);
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
        if (!(isNoun(nounA) && isNoun(nounB))) {
            throw new IllegalArgumentException("Both nouns must be present in wordnet");
        }
        if (nounA.equals(nounB)) return 0;
        return sap.length(nounMap.get(nounA), nounMap.get(nounB));
    }


    // a synset that is the common ancestor of nounA
    // and nounB in a shortest anncestral path
    public String sap(String nounA, String nounB) {
        if (!(isNoun(nounA) && isNoun(nounB))) {
            throw new IllegalArgumentException("Both nouns must be present in wordnet");
        }
        int ancestor = sap.ancestor(nounMap.get(nounA), nounMap.get(nounB));
        return synsets.get(ancestor);
    }

}