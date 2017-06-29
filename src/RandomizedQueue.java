import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Kostiantyn Kostin on 24.06.2017.
 */
public class RandomizedQueue<Item> extends Deque<Item> {

    private class RandomizedIterator implements Iterator<Item> {

        private int[] randIndexes;
        private int index = 0;

        public RandomizedIterator() {
            randIndexes = new int[size()];
            for (int i = 0; i < randIndexes.length; i++) {
                randIndexes[i] = i;
            }
            StdRandom.shuffle(randIndexes);
        }

        @Override
        public boolean hasNext() {
            return index < randIndexes.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return getNode(randIndexes[index++]).item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     *  Construct an empty randomized queue
     */
    public RandomizedQueue() {}

    /**
     * Add the item
     * @param item
     */
    public void enqueue(Item item) {
        addLast(item);
    }

    /**
     * Remove and return a random item
     * @return
     */
    public Item dequeue() {
        Node node = getRandomNode();
        size--;
        if (size() > 0) {
            Node nextNode = node.next;
            Node prevNode = node.prev;

            if (nextNode != null) {
                nextNode.prev = prevNode;
            } else {
                first = prevNode;
            }

            if (prevNode != null) {
                prevNode.next = nextNode;
            } else {
                last = nextNode;
            }
        } else {
            first = null;
            last = null;
        }

        return node.item;
    }

    /**
     * Return (but do not remove) a random item
     * @return
     */
    public Item sample() {
        return getRandomNode().item;
    }

    private Node getRandomNode() {
        return getNode((int) (StdRandom.uniform() * size()));
    }

    private Node getNode(int index) {
        if (isEmpty()) throw new NoSuchElementException();
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
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
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

        while (!randomizedQueue.isEmpty()) {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}
