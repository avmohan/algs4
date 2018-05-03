package io.github.avmohan.coursera.algs4.part2.week1;

public class Outcast {

    private final WordNet wordNet;

    public Outcast(WordNet wordNet) {
        if (wordNet == null) {
            throw new IllegalArgumentException("wordnet must not be null");
        }
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns) {
        String outcast = null;
        int maxDist = -1;
        for (int i = 0; i < nouns.length; i++) {
            int sum = 0;
            for (int j = 0; j < nouns.length; j++) {
                sum += wordNet.distance(nouns[i], nouns[j]);
            }
            if (sum > maxDist) {
                maxDist = sum;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

}
