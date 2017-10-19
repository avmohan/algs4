package io.github.avmohan.coursera.algs4.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by avmohan on 19/10/17.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node front;
    private Node rear;
    private int size;

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Null items not allowed");
        size++;
        Node newNode = new Node(item);
        newNode.next = front;
        if (front != null) front.prev = newNode;
        front = newNode;
        if (rear == null) rear = front;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Null items not allowed");
        size++;
        Node newNode = new Node(item);
        newNode.prev = rear;
        if (rear != null) rear.next = newNode;
        rear = newNode;
        if (front == null) front = rear;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Remove from empty deque");
        size--;
        Node leavingNode = front;
        front = front.next;
        if (front != null) front.prev = null;
        if (rear == leavingNode) rear = null;
        return leavingNode.item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Remove from empty deque");
        size--;
        Node leavingNode = rear;
        rear = rear.prev;
        if (rear != null) rear.next = null;
        if (front == leavingNode) front = null;
        return leavingNode.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node cur = front;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException("Iterator has exhausted");
                Item item = cur.item;
                cur = cur.next;
                return item;
            }

        };
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item item) {
            this.item = item;
        }

    }

}
