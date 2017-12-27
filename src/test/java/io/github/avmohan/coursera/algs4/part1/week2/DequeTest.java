package io.github.avmohan.coursera.algs4.part1.week2;

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
class DequeTest {
    @Test
    void testThatAddFirstWithNullItemThrowsIllegalArgument() throws Exception {
        Deque<Integer> deque = new Deque<>();
        assertThatIllegalArgumentException()
            .isThrownBy(() -> deque.addFirst(null))
            .withMessage("Null items not allowed");
    }

    @Test
    void testThatAddLastWithNullItemThrowsIllegalArgument() throws Exception {
        Deque<Integer> deque = new Deque<>();
        assertThatIllegalArgumentException()
            .isThrownBy(() -> deque.addLast(null))
            .withMessage("Null items not allowed");
    }

    @Test
    void testThatRemoveFirstOnEmptyDequeThrowsNoSuchElement() throws Exception {
        Deque<Integer> deque = new Deque<>();
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(deque::removeFirst)
            .withMessage("Remove from empty deque");
    }

    @Test
    void testThatRemoveLastOnEmptyDequeThrowsNoSuchElement() throws Exception {
        Deque<Integer> deque = new Deque<>();
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(deque::removeLast)
            .withMessage("Remove from empty deque");
    }

    @Test
    void testThatNextAfterIteratorExhaustionThrowsNoSuchElement() throws Exception {
        Deque<Integer> deque = new Deque<>();
        Iterator iterator = deque.iterator();
        while (iterator.hasNext()) iterator.next();
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(iterator::next)
            .withMessage("Iterator has exhausted");
    }

    @Test
    void testThatRemoveThrowsUnsupportedOperation() throws Exception {
        Deque<Integer> deque = new Deque<>();
        Iterator iterator = deque.iterator();
        assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(iterator::remove)
            .withMessage("remove");
    }

    @Test
    void testThatAddFirstAddsElementToFront() throws Exception {
        Deque<Integer> deque = new Deque<>();
        IntStream.rangeClosed(1, 4).forEach(deque::addFirst);
        assertThat(deque).hasSize(4).containsExactly(4, 3, 2, 1);
    }

    @Test
    void testThatAddLastAddsElementToRear() throws Exception {
        Deque<Integer> deque = new Deque<>();
        IntStream.rangeClosed(1, 4).forEach(deque::addLast);
        assertThat(deque).hasSize(4).containsExactly(1, 2, 3, 4);
    }

    @Test
    void testThatRemoveFirstRemovesElementsFromFront() throws Exception {
        Deque<Integer> deque = new Deque<>();
        IntStream.rangeClosed(1, 4).forEach(deque::addLast);
        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque).hasSize(3).containsExactly(2, 3, 4);
    }

    @Test
    void testThatRemoveLastRemovesElementsFromRear() throws Exception {
        Deque<Integer> deque = new Deque<>();
        IntStream.rangeClosed(1, 4).forEach(deque::addLast);
        assertThat(deque.removeLast()).isEqualTo(4);
        assertThat(deque).hasSize(3).containsExactly(1, 2, 3);
    }

    @Test
    void testNonEmptyToEmptyAndBackToNonEmpty() throws Exception {
        Deque<Integer> deque = new Deque<>();
        IntStream.rangeClosed(1, 3).forEach(deque::addLast);
        assertThat(deque).hasSize(3).containsExactly(1, 2, 3);
        IntStream.rangeClosed(1, 3).forEach(x -> deque.removeFirst());
        assertThat(deque).isEmpty();
        assertThat(deque.isEmpty()).isEqualTo(true);
        IntStream.rangeClosed(1, 3).forEach(deque::addLast);
        assertThat(deque).hasSize(3).containsExactly(1, 2, 3);
    }


    @Test
    void testMultipleOperationsForConsistency() throws Exception {
        Deque<Integer> deque = new Deque<>();
        IntStream.rangeClosed(1, 3).forEach(deque::addLast);
        assertThat(deque).hasSize(3).containsExactly(1, 2, 3);
        IntStream.rangeClosed(4, 6).forEach(deque::addFirst);
        assertThat(deque).hasSize(6).containsExactly(6, 5, 4, 1, 2, 3);
        deque.addLast(deque.removeFirst());
        assertThat(deque).hasSize(6).containsExactly(5, 4, 1, 2, 3, 6);
        deque.addFirst(deque.removeLast());
        assertThat(deque).hasSize(6).containsExactly(6, 5, 4, 1, 2, 3);
        IntStream.range(0, 3).forEach(x -> deque.removeFirst());
        assertThat(deque.size()).isEqualTo(3);
        IntStream.range(0, 3).forEach(x -> deque.removeLast());
        assertThat(deque).isEmpty();
        assertThat(deque.isEmpty()).isEqualTo(true);
    }

}