package io.github.avmohan.coursera.algs4.part1.week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by avmohan on 20/10/17.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> strings = new RandomizedQueue<>();
        // Reservoir sampling.
        // Add first k items. Starting from (k+1)th element, for the ith element, replace a random
        // element in the queue with the new ith element with probability k/i
        int i = 0;
        while (!StdIn.isEmpty()) {
            i++;
            String string = StdIn.readString();
            if (i <= k) {
                strings.enqueue(string);
            } else {
                if (StdRandom.uniform() < ((double) k) / i) {
                    strings.dequeue();
                    strings.enqueue(string);
                }
            }
        }
        for (String string : strings) {
            StdOut.println(string);
        }
    }
}
