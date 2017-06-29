import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Kostiantyn Kostin on 24.06.2017.
 */
public class RandomizedQueue<E> extends Deque<E> {

    /**
     *  Construct an empty randomized queue
     */
    public RandomizedQueue() {}

    /**
     * Add the item
     * @param item
     */
    public void enqueue(E item) {
        addLast(item);
    }

    /**
     * Remove and return a random item
     * @return
     */
    public E dequeue() {
        Node node = getRandomNode();
        size--;
        if (size > 0) {
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {

            }
        } else {
            first = null;
            last = null;
        }

        // TODO return RANDOM item!!!!
        return node.item;
    }

    /**
     * Return (but do not remove) a random item
     * @return
     */
    public E sample() {
        return getRandomNode().item;
    }

    private Node getRandomNode() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = (int) (StdRandom.uniform() * size);
        Node node = last;
        while (index > 0) {
            node = node.next;
            index--;
        }
        return node;
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
