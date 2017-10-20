package io.github.avmohan.coursera.algs4.week2;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by avmohan on 19/10/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    /*
    The elements will be stored in items[0..size-1]. To remove a random element, pick a random
    element, swap with the last element and shrink
     */

    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Null items not allowed");
        if (size == items.length) {
            resizeItems(items.length * 2);
        }
        items[size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int index = StdRandom.uniform(size);
        Item item = items[index];
        items[index] = items[size - 1];
        items[size - 1] = null;
        size--;
        if (size < items.length / 4) {
            resizeItems(items.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return items[StdRandom.uniform(size)];
    }

    private void resizeItems(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }


    private class RandomizedQueueIterator implements Iterator<Item> {
        int[] indices;
        int cur;

        RandomizedQueueIterator() {
            cur = 0;
            indices = new int[size];
            for (int i = 0; i < size; i++) {
                indices[i] = i;
            }
            StdRandom.shuffle(indices);
        }

        @Override
        public boolean hasNext() {
            return cur != size;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Iterator has exhausted");
            return items[indices[cur++]];
        }
    }
}
