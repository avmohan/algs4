package io.github.avmohan.coursera.algs4.week2;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by avmohan on 19/10/17.
 */
public class RandomizedQueue<T> implements Iterable<T> {
    /*
    The elements will be stored in items[0..size-1]. To remove a random element, pick a random
    element, swap with the last element and shrink
     */

    private T[] items;
    private int size;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        items = (T[]) new Object[2];
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public int size() {
        return size;
    }

    public void enqueue(T item) {
        if (item == null) throw new IllegalArgumentException("Null items not allowed");
        if (size == items.length) {
            resizeItems(items.length * 2);
        }
        items[size] = item;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int index = StdRandom.uniform(size);
        T item = items[index];
        items[index] = items[size - 1];
        items[size - 1] = null;
        size--;
        if (size <= items.length / 4) {
            resizeItems(items.length / 2);
        }
        return item;
    }

    public T sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return items[StdRandom.uniform(size)];
    }

    private void resizeItems(int newSize) {
        @SuppressWarnings("unchecked")
        T[] newItems = (T[]) new Object[newSize];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    @Override
    public Iterator<T> iterator() {
        return new RandomizedQueueIterator();
    }


    private class RandomizedQueueIterator implements Iterator<T> {
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
        public T next() {
            if (!hasNext()) throw new NoSuchElementException("Iterator has exhausted");
            return items[indices[cur++]];
        }
    }
}
