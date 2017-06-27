import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Kostiantyn Kostin on 24.06.2017.
 */
public class RandomizedQueue<E> {

    private int size = 0;
    private Node last;

    private class Node {
        Node next;
        E item;

        public Node(Node next, E item) {
            this.next = next;
            this.item = item;
        }
    }

    /**
     *  construct an empty randomized queue
     */
    public RandomizedQueue() {

    }

    /**
     * is the queue empty?
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * return the number of items on the queue
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * add the item
     * @param item
     */
    public void enqueue(E item) {
        if (item == null) throw new IllegalArgumentException();
        Node node = new Node(last, item);
        last = node;
        size++;
    }

    /**
     * Remove and return a random item
     * @return
     */
    public E dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = (int) (StdRandom.uniform() * size);
        Node node = last;
        Node prev = null;
        while (index > 0) {
            prev = node;
            node = node.next;
            index--;
        }
        prev.next = node.next;
        size--;
        return node.item;
    }

    /**
     * Return (but do not remove) a random item
     * @return
     */
    public E sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = (int) (StdRandom.uniform() * size);
        Node node = last;
        while (index > 0) {
            node = node.next;
            index--;
        }
        return node.item;
    }

    /**
     * Return an independent iterator over items in random order
     * @return
     */
    public Iterator<E> iterator() {
        return null;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue();
        for (int i = 0; i < 10; i++) {
            randomizedQueue.enqueue(i);
        }

        int[] stats = new int[10];
        for (int i = 0; i < 10000; i++) {
            //System.out.println("rnd = " + randomizedQueue.sample());
            stats[randomizedQueue.sample()]++;
        }

        for (int i = 0; i < stats.length; i++) {
            System.out.println(stats[i]);
        }

//        while (!randomizedQueue.isEmpty()) {
//            System.out.println(randomizedQueue.dequeue());
//        }
    }
}
