package io.github.avmohan.coursera.algs4.week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;

/**
 * Created by avmohan on 20/10/17.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> strings = new RandomizedQueue<>();
        while (true) {
            try {
                strings.enqueue(StdIn.readString());
            } catch (NoSuchElementException e) {
                break;
            }
        }
        for (String string : strings) {
            if (k == 0) break;
            StdOut.println(string);
            k--;
        }
    }
}
