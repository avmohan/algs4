package io.github.avmohan.coursera.algs4.week2;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

/**
 * Created by avmohan on 19/10/17.
 */
class RandomizedQueueTest {

    @Test
    void testEnqueueNullThrowsIllegalArgument() throws Exception {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        assertThatIllegalArgumentException()
            .isThrownBy(() -> q.enqueue(null))
            .withMessage("Null items not allowed");
    }

    @Test
    void testSampleWhenEmptyThrowsNoSuchElement() throws Exception {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(q::sample)
            .withMessage("Queue is empty");
    }

    @Test
    void testDequeueWhenEmptyThrowsNoSuchElement() throws Exception {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(q::dequeue)
            .withMessage("Queue is empty");
    }

    @Test
    void testRemoveOnIteratorThrowsUnsupportedOperation() throws Exception {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        Iterator<Integer> it = q.iterator();
        assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(it::remove)
            .withMessage("remove");
    }

    @Test
    void testEnqueues() throws Exception {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        IntStream.range(0, 100).forEach(q::enqueue);
        assertThat(q.size()).isEqualTo(100);
    }

    @Test
    void testDequeues() throws Exception {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        IntStream.range(0, 100).forEach(q::enqueue);
        assertThat(q.size()).isEqualTo(100);
        IntStream.range(0, 100).forEach(x -> q.dequeue());
        assertThat(q.isEmpty()).isTrue();
    }

    @Test
    void testNextOnExhaustedIteratorThrowsNoSuchElement() throws Exception {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        IntStream.range(0, 3).forEach(q::enqueue);
        Iterator<Integer> it = q.iterator();
        IntStream.range(0, 3).forEach(x -> it.next());
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(it::next)
            .withMessage("Iterator has exhausted");
    }

    @Test
    void testViaIterator() throws Exception {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        IntStream.of(1, 4, -1, 3, 9).forEach(q::enqueue);
        assertThat(q).hasSize(5).containsOnly(-1, 1, 4, 3, 9);
    }

    @Test
    void testSampling() throws Exception {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        IntStream.rangeClosed(1, 100).forEach(q::enqueue);
        assertThat(q.sample()).isBetween(1, 100);
    }

}